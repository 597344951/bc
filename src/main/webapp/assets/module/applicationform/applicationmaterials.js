window.appInstince = new Vue({
    el: '#app',
    data: {
        formList: [],
        form: {},
        formInfo: {},
        formFieldGroup:[],
        formFields: [{
            label: '姓名',
            type: 'text',
            name: 'name'
        }, {
            label: '性别',
            type: 'radio',
            name: 'sex',
            fieldValues: [{
                label: '男',
                value: 'boy'
            }, {
                label: '女',
                value: 'girl'
            }]
        }, {
            label: '性别',
            type: 'select',
            name: 'sex2',
            fieldValues: [{
                label: '男',
                value: 'boy'
            }, {
                label: '女',
                value: 'girl'
            }]
        }, {
            label: '描述',
            type: 'textarea',
            name: 'descript'
        }]
    },
    mounted() {
        this.loadFormData();
    },
    computed: {},
    methods: {
        loadFormData() {
            let url = '/application-form/form/query';
            ajax_json_promise(url, 'post', {}).then(result => {
                if (result.status) {
                    this.formList = result.data;
                }
            });
        },
        changeForm(form) {
            this.formInfo = form;
            this.toGroupFiels(form.fields);
        },
        toGroupFiels(fields){
            let ay = [];
            fields.forEach(element => {
                let ny = ay[element.rowIndex] || [];
                ny.push(element);
                ay[element.rowIndex] = ny;
            });
            this.formFieldGroup = ay;
        },
        submitForm() {
            let formId = this.formInfo.formId;
            let url = `/application-user/form-data/${formId}`;
            ajax_json_promise(url, 'post', this.form).then(result => {
                if (result.status) {
                    $message(result.msg, 'success', this);
                    this.resetForm();
                } else {
                    $message(result.msg, 'danger', this);
                }
            });
        },
        resetForm() {
            this.form = {};
        }

    }
})