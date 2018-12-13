<template>
  <div :id="id" class="pw_container">
    <div class="pw_photo_box" v-for="(photo,index) in photos" :key="index">
      <div class="pw_photo">
        <img :src="photo.src" :alt="photo.alt" />
      </div>
    </div>
  </div>
</template>
<script>
import wall from "./wall";
export default {
  name: "photo-wall",
  label: "照片墙",
  requirement: 'Vue, jquery, jquery-ui, jquery-transform',
  props: ["id", "photos"],
  mounted() {
    wall.decorate(`#${this.id}`);
  },
  watch: {
    photos(newVal, oldVal) {
      setTimeout(() => {
        console.log('内容发生变化重新装饰...')
        wall.decorate(`#${this.id}`);
      }, 200);
    }
  }
};
</script>
<style scoped>
.pw_container {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  overflow: hidden;
}
.pw_photo_box {
  position: absolute;
  z-index: 10;
  top: 0px;
  left: 0px;
  display: none;
  -moz-border-radius: 5px;
  -webkit-border-radius: 5px;
  border-radius: 5px;
}

.pw_photo_box img {
  max-width: 250px;
  max-height: 250px;
  box-shadow: 1px 1px 5px #555;
  border-radius: 5px;
}
</style>
