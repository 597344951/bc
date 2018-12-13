
<template>
  <ul class="wrap"></ul>
</template>
<script>
import Wrap1 from "./wrap1.js";
export default {
  name: "photo-wrap-1",
  label: "图片预览",
  props: ["photos", "delay"],
  data() {
    return {
      curPhotoIndex: 0,
      wrap: null,
      timer: null
    };
  },
  mounted() {
    this.wrap = new Wrap1();
    this.wrap.load(this.photos[this.curPhotoIndex].src, () => {
      this.wrap.show();
    });
    this.timer = setInterval(() => {
      this.curPhotoIndex ++;
    }, this.delay);
  },
  watch: {
    curPhotoIndex() {
      if (this.curPhotoIndex == this.photos.length) {
        this.curPhotoIndex = 0;
      } else if (this.curPhotoIndex == -1) {
        this.curPhotoIndex = this.photos.length - 1;
      } else {
        this.wrap.hide();
        setTimeout(() => {
          this.wrap.load(this.photos[this.curPhotoIndex].src, () => {
            this.wrap.show();
          });
        }, this.delay / 5);
      }
    }
  },
  methods: {
    pre() {
      this.curPhotoIndex --;
    },
    next() {
      this.curPhotoIndex ++;
    },
    stop() {
      clearInterval(this.timer);
    }
  }
};
</script>
<style>
.wrap {
  list-style: none;
  margin: auto;
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
}
.wrap li {
  position: absolute;
  left: 0;
  top: 0;
  transition: transform 1500ms ease-out;
  background-color: white;
}
.wrap .image {
  width: 100%;
  height: 100%;
  background-size: cover;
  transition: transform 1500ms ease-out;
}
</style>


