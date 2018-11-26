Vue.use(VueDragging)
const app = new Vue({
    el: '#app',
    data: {
        curTab: module ? module : 'photos',
        wrap: {
            url: 'videos' == module ? 'view/pw/video' : '',
            effect: '',
            minimum: 'videos' == module ? 2 : 0,
            maximum: 'videos' == module ? 6 : 0,
            backgroundImage: '',
            backgroundImageCover: '',
            backgroundColor: '',
            backgroundMusic: '',
            backgroundMusicCover: '',
            title: '',
            photos: [],
            xx: {
                x1: '',
                x2: '',
                x3: '',
                x4: '',
                x5: '',
                x6: '',
                x7: '',
                x8: '',
                x9: '',
                x10: '',
                x11: '',
                x12: '',
                x13: ''
            },
            demoUrl: '',
            tips:'',
            titleTheme: ''
        },
        wrapJsonStr: '',
        effectSelecterShow: false,
        titleThemeSelecterShow: false,
        effects: {
            active: '',
            current: null,
            groups: []
        },
        curTitleTheme: '',
        titleThemes: [],
        wraps: {
            pageSize: 10
        },
        materialExplorerShow: false,
        curSelectedFrom: ''
    },
    mounted() {
        get(effectsFile, resp => {
            this.effects.groups = resp.groups;
            this.effects.active = resp.groups[0].name
        })

        get(titleThemeFile, resp => {
            this.titleThemes = resp
            this.titleThemes.forEach(t => {
                t.thumbnail = imageHost + t.thumbnail
            })
        })

        this.loadWraps(1, this.wraps.pageSize)
    },
    methods: {
        onTabClick(tab, event) {
            if ('photos' == tab.name) {
                this.wrap = {
                    url: '',
                    effect: '',
                    minimum: 0,
                    maximum: 0,
                    backgroundImage: '',
                    backgroundColor: '',
                    backgroundMusic: '',
                    title: '',
                    photos: [],
                    xx: {
                        x1: '',
                        x2: '',
                        x3: '',
                        x4: '',
                        x5: '',
                        x6: '',
                        x7: '',
                        x8: '',
                        x9: '',
                        x10: '',
                        x11: '',
                        x12: '',
                        x13: ''
                    },
                    demoUrl: '',
                    tips:'',
                    titleTheme: ''
                }
            } else if ('videos' == tab.name) {
                this.wrap = {
                    url: 'view/pw/video',
                    effect: '',
                    minimum: 2,
                    maximum: 6,
                    backgroundImage: '',
                    backgroundColor: '',
                    backgroundMusic: '',
                    title: '',
                    photos: [],
                    xx: {
                        x1: '',
                        x2: '',
                        x3: '',
                        x4: '',
                        x5: '',
                        x6: '',
                        x7: '',
                        x8: '',
                        x9: '',
                        x10: '',
                        x11: '',
                        x12: '',
                        x13: ''
                    },
                    demoUrl: '',
                    tips:'',
                    titleTheme: ''
                }
            }
        },
        onSuccess(response, file, fileList) {
            if ('SUCCESS' === response.state) {
                let photo = {
                    key: response.title,
                    url: response.url,
                    original: response.original,
                    thumbnail: response.thumbnail ? imageHost + response.thumbnail : imageHost + response.url,
                    screenshot: response.screenshot ? imageHost + response.screenshot : '/images/video.png'
                }
                if (!isContain(this.wrap.photos, photo)) this.wrap.photos.push(photo)
            }
        },
        onBackgroundImageSuccess(response, file, fileList) {
            if ('SUCCESS' === response.state) {
                this.wrap.backgroundImage = response.url
            }
        },
        onBackgroundImageRemove(file, fileList) {
            this.wrap.backgroundImage = ''
        },
        onBackgroundMusicSuccess(response, file, fileList) {
            if ('SUCCESS' === response.state) {
                this.wrap.backgroundMusic = response.url
            }
        },
        onBackgroundMusicRemove(file, fileList) {
            this.wrap.backgroundMusic = ''
        },
        onRemove(file) {
            this.wrap.photos.splice(this.wrap.photos.indexOf(file), 1)
        },
        onEffectClick(effect) {
            this.effects.current = effect
        },
        onEffectSelected() {
            if (this.effects.current) {
                this.wrap.url = this.effects.current.url
                this.wrap.effect = this.effects.current.effect
                this.wrap.minimum = this.effects.current.minimum
                this.wrap.maximum = this.effects.current.maximum
                this.wrap.demoUrl = this.effects.current.demoUrl
                this.wrap.tips = this.effects.current.tips

                if (this.effects.current.tool == 'M2') {
                    this.initM2("m2")
                }
            }
            this.effectSelecterShow = false;
        },
        onPrePreview() {
            if (!this.submitPreCheck()) return;
            this.wrapJsonStr = JSON.stringify(this.wrap)
            setTimeout(() => {
                $('#wrapJsonStrForm').submit()
            }, 100)
        },
        onPreview(row) {
            window.open("/pw/preview/" + row.silhouette_id, "_blank")
        },
        onSubmit() {
            if (!this.submitPreCheck()) return;
            post("/pw/addxjdx", this.wrap, resp => {
                if (resp.status) {
                    this.$message({
                        message: '制作成功.',
                        type: 'success'
                    });
                    this.loadWraps(1, this.wraps.pageSize)
                    this.wrap = {
                        url: '',
                        effect: '',
                        minimum: 0,
                        maximum: 0,
                        backgroundImage: '',
                        backgroundColor: '',
                        backgroundMusic: '',
                        title: '',
                        photos: [],
                        xx: {
                            x1: '',
                            x2: '',
                            x3: '',
                            x4: '',
                            x5: '',
                            x6: '',
                            x7: '',
                            x8: '',
                            x9: '',
                            x10: '',
                            x11: '',
                            x12: '',
                            x13: ''
                        },
                        demoUrl: '',
                        tips:'',
                        titleTheme: ''
                    }
                    this.curTab = 'list'
                } else {
                    this.$message.error('制作失败: ' + resp.msg)
                }
            })
        },
        onCurrentPageChange(pageNum) {
            this.loadWraps(pageNum, this.wraps.pageSize)
        },
        submitPreCheck() {
            if (!this.wrap.url) {
                this.$message.error('请先选着一个模板开始制作。')
                return false
            }
            if (!this.wrap.title) {
                this.$message.error('请输入主题名称内容。')
                return false
            }
            if (this.wrap.photos.length > this.wrap.maximum || this.wrap.photos.length < this.wrap.minimum) {
                this.$message.error('请选着指定数量范围的图片数。')
                return false
            }
            return true;
        },
        loadWraps(pageNum, pageSize) {
            get("/pw/listXjdx/" + pageNum + "/" + pageSize, resp => {
                if (resp.status) {
                    this.wraps = resp.data;
                }
            })
        },
        onTitleThemeSelected(theme) {
            this.wrap.titleTheme = theme.url
            this.curTitleTheme = theme.thumbnail
            this.titleThemeSelecterShow = false
        },
        onDelete(row) {
            get("/pw/delete/" + row.silhouette_id, resp => {
                if (resp.status) {
                    this.$message({
                        message: '删除成功.',
                        type: 'success'
                    });
                    this.loadWraps(this.wraps.pageNum, this.wraps.pageSize)
                }
            })
        },
        initM2(container) {
            xiuxiu.setLaunchVars("quality", 100)
            xiuxiu.embedSWF(container, 2, "100%", "100%");
            xiuxiu.setUploadURL(`${imageHost}/image`);
            xiuxiu.setUploadType(2);
            xiuxiu.setUploadDataFieldName("file");
            xiuxiu.onUploadResponse = function (response) {
                response = JSON.parse(response)
                if ('SUCCESS' === response.state) {
                    app.wrap.photos = [{
                        key: response.title,
                        url: response.url,
                        original: response.original,
                        thumbnail: response.thumbnail ? imageHost + response.thumbnail : imageHost + response.url,
                        screenshot: response.screenshot ? imageHost + response.screenshot : '/images/video.png'
                    }]
                }
            }
        },
        onMaterialSelected(material) {
            if (material.length > 0) {
                if (this.curSelectedFrom == 'background') {
                    this.wrap.backgroundImage = material[0].url
                    this.wrap.backgroundImageCover = imageHost + material[0].coverUrl
                } else if ('backgroundMusic' == this.curSelectedFrom) {
                    this.wrap.backgroundMusic = material[0].url
                    this.wrap.backgroundMusicCover = imageHost + material[0].coverUrl
                } else if ('photos' == this.curSelectedFrom) {
                    material.forEach(m => {
                        this.wrap.photos.push({
                            key: m.materialId,
                            url: m.url,
                            original: m.name,
                            thumbnail: imageHost + m.coverUrl,
                        })
                    })

                }
            }

            console.log(material)
        }
    }
})

function isContain(list, obj) {
    for (let i in list) {
        if (obj.key === list[i].key) {
            return true
        }
    }
    return false
}

function get(url, callback, errorCallback) {
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        complete: (XMLHttpRequest, status) => {},
        success: resp => {
            if (callback) callback(resp)
        },
        error: (jqXHR, textStatus, errorThrown) => {
            console.error(errorThrown)
            if (errorCallback) errorCallback(jqXHR, textStatus, errorThrown)
        }
    })
}

function post(url, postData, callback, errorCallback) {
    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(postData),
        complete: (XMLHttpRequest, status) => {},
        success: resp => {
            if (callback) callback(resp)
        },
        error: (jqXHR, textStatus, errorThrown) => {
            console.error(errorThrown)
            if (errorCallback) errorCallback(jqXHR, textStatus, errorThrown)
        }
    })
}