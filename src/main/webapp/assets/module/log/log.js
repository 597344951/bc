  var appInstince = new Vue({
      el: '#app',
      data: {
          loading: false,
          log: {
              type: ''
          },
          pager: {
              currentPage: 1,
              "limit": 14,
              "total": 0,
          },
          logs: [],
          logTypes: [],
          levels: [{
                  label: 'DEBUG',
                  value: 'DEBUG'
              },
              {
                  label: 'INFO',
                  value: 'INFO'
              },
              {
                  label: 'WARN',
                  value: 'WARN'
              },
              {
                  label: 'ERROR',
                  value: 'ERROR'
              },
              {
                  label: 'FATAL',
                  value: 'FATAL'
              }
          ],
          form: {
              name: 'test'
          }
      },
      mounted() {
          this.loadLogType();
          this.queryLog();
      },
      methods: {
          loadLogType() {
              var ins = this;
              ajax("/log/type", "get", "", function (result) {
                  ins.logTypes = result.data;
              });
          },
          queryBtnHandle() {
              this.pager.currentPage = 1;
              this.queryLog();
          },
          queryLog() {
              this.loading = true;
              var ins = this;
              let st = ins.pager.limit * (ins.pager.currentPage - 1);
              let ed = ins.pager.limit;
              var url = '/log/log/' + st + '-' + ed;
              ajax_json(url, "post", ins.log, function (result) {
                  ins.logs = result.data;
                  ins.pager.total = result.pager.total;
                  ins.loading = false;
              });
          },
          handleSizeChange(val) {
              //console.log(`每页 ${val} 条`);
              this.pager.limit = val;
              this.queryLog();
          },
          handleCurrentChange(val) {
              //console.log(`当前页: ${val}`);
              this.pager.currentPage = val;
              this.queryLog();
          },
          level_dis(target) {
              if (target.level == 'DEBUG') return '';
              if (target.level == 'INFO') return 'info';
              if (target.level == 'WARN') return 'warning';
              if (target.level == 'ERROR') return 'danger';
              if (target.level == 'FATAL') return 'danger';
              return '';
          },
          logType_dis(target) {
              var t = this.logTypes.filter((lt) =>
                  lt.tid == target.type
              ).map((i) => i.name);
              return t.join();
          },
          disJson(json) {
              let str = JSON.stringify(JSON.parse(json), null, '  ');
              return syntaxHighlight(str);
          },
          split(content){
              return content.split('\n');
          }
      }
  });

  function syntaxHighlight(json) {
      if (typeof json != 'string') {
          json = JSON.stringify(json, undefined, 2);
      }
      json = json.replace(/&/g, '&').replace(/</g, '<').replace(/>/g, '>');
      return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
          var cls = 'number';
          if (/^"/.test(match)) {
              if (/:$/.test(match)) {
                  cls = 'key';
              } else {
                  cls = 'string';
              }
          } else if (/true|false/.test(match)) {
              cls = 'boolean';
          } else if (/null/.test(match)) {
              cls = 'null';
          }
          return '<span class="' + cls + '">' + match + '</span>';
      });
  }