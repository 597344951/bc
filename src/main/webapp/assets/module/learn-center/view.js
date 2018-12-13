import serverConfig from '/environment/resourceUploadConfig.jsp';

let ins = new Vue({
  el: "#app",
  data: {
    resourceData:{}
  },
  mounted() {
    let rd = window.resourceData
    if(rd) {
      this.resourceData = rd
    }
  },
  computed: { 
    
  },
  watch: {
  },
  methods: {
    getResUrl(url) {
      return serverConfig.getUrl(url);
    },
  }
});


window.appInstince = ins;