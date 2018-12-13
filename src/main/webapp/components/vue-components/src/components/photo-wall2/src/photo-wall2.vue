<template>
    <div :id="id" class="pw_container hide">
        <div class="pw_wrapper">
            <ul class="pw_thumbs">
                <li v-for="(photo, index) in photos" :key="index"><img :src="photo.src" :data-img="photo.src" :alt="photo.alt" />
                    <div>
                        <h2>{{photo.name}}</h2>
                        <p>{{photo.desc}}</p>
                    </div>
                </li>
            </ul>
        </div>
        <div class="pw_ribbon">
            <span class="pw_close"></span>
            <span class="pw_zoom"></span>
        </div>
    </div>
</template>
<script>
import wall from "./wall2.js";
export default {
  name: "photo-wall2",
  label: "照片墙",
  props: ["id", "photos"],
  mounted() {
    wall.decorate(`#${this.id}`);
  },
  watch: {
    photos(newVal, oldVal) {
      setTimeout(() => {
        wall.decorate(`#${this.id}`);
      }, 200);
    }
  }
};
</script>
<style scoped>
.hide {
  display: none;
}
ol,
ul {
  list-style: none;
}
h2,
p {
  margin: 0px;
  word-wrap: break-word;
}
ul.pw_thumbs li {
  float: left;
  margin: 5px;
}
ul.pw_thumbs li div {
  position: absolute;
  top: 5px;
  width: 280px;
  padding: 0px 10px;
  display: none;
  color: #fff;
  z-index: 100;
}
ul.pw_thumbs li div h2 {
  font-family: "Wire One", arial, serif;
  font-size: 38px;
  text-transform: uppercase;
  text-shadow: 0px 0px 1px #fff;
}
ul.pw_thumbs li div p {
  font-size: 11px;
  line-height: 16px;
  font-style: italic;
}
ul.pw_thumbs li:nth-child(1) {
  margin-left: 50px;
}
ul.pw_thumbs li:nth-child(even) {
  margin-top: 30px;
}
ul.pw_thumbs li:nth-child(3n) {
  margin-left: 20px;
}
ul.pw_thumbs li img {
  border: 5px solid #fff;
  cursor: pointer;
  position: relative;
  -moz-box-shadow: 1px 1px 1px #aaa;
  -webkit-box-shadow: 1px 1px 1px #aaa;
  box-shadow: 1px 1px 1px #aaa;
}
ul.pw_thumbs li img:hover {
  -moz-box-shadow: 1px 1px 7px #777;
  -webkit-box-shadow: 1px 1px 7px #777;
  box-shadow: 1px 1px 7px #777;
}
ul.pw_thumbs li:nth-child(even) img {
  height: 20px;
}
ul.pw_thumbs li:nth-child(odd) img {
  height: 40px;
}
ul.pw_thumbs li:nth-child(5n) img {
  height: 70px;
}
ul.pw_thumbs li:nth-child(6n) img {
  height: 110px;
}
ul.pw_thumbs li:nth-child(7n) img {
  height: 120px;
}
.pw_ribbon {
  position: fixed;
  height: 126px;
  width: 0px;
  left: 0px;
  top: 0px;
  background: #000;
  opacity: 0.8;
  z-index: 10;
  overflow: hidden;
  display: none;
}
.pw_close {
  position: absolute;
  top: 10px;
  right: 10px;
  background: #f0f0f0 url(./images/close.gif) no-repeat center center;
  width: 18px;
  height: 18px;
  display: none;
  cursor: pointer;
}
.pw_zoom {
  color: white;
  font-size: 8px;
  font-family: Arial, sans-serif;
  text-transform: uppercase;
  padding: 14px;
  display: none;
  float: right;
  margin-right: 30px;
}
.pw_ribbon img {
  position: absolute;
  top: 50%;
  left: 50%;
  border: 2px solid #fff;
}
.pw_loading {
  background: #fff url(./images/loader.gif) no-repeat center center;
  width: 28px;
  height: 28px;
  position: absolute;
  top: 50%;
  left: 50%;
  z-index: 10000;
  margin: -14px 0px 0px -14px;
  opacity: 0.8;
}
</style>
