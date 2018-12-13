<template>
  <div class="app-box" id="app-box">
    <div class="center" v-html="htmlBox"></div>
  </div>
</template>

<script>
export default {
  name: "flashing",
  label: "文字快闪",
  props: ["words", "loop", "delay"],
  data() {
    return {
      playWords: [],
      htmlBox: ""
    };
  },
  mounted() {
    const random = num => {
      return parseInt(Math.random() * num);
    };
    const setArea = () => {
      return {
        position: "absolute",
        top: 10 + random(50) + "%",
        "font-size": 20 + random(20) + "px",
        transform: `rotate(${random(10) - random(30)}deg) translate(${
          random(50) % 2 == 1 ? random(100) : -random(200)
        }px,0)`
      };
    };
    const demo = words => {
      if (words.length < 1) {
        return;
      }
      let str = "";
      words[0].forEach(x => {
        let style =
          words.length == 1
            ? {
                "font-size": "60px",
                position: "absolute",
                top: "40%",
                transform: "transform:translateX(-50%);"
              }
            : setArea();
        let s = "";
        for (let k in style) {
          if (words[0].length == 1 && k == "transform") {
            s += `transform:translateX(-50%);`;
            continue;
          }
          s += `${k}:${style[k]};`;
        }
        str += `<span style="${s}">${x}</span>`;
      });
      this.htmlBox = str;
      setTimeout(x => {
        words.splice(0, 1);
        demo(words);
      }, this.delay);
    };
    this.playWords = JSON.parse(JSON.stringify(this.words));
    demo(this.playWords);
    if (this.loop) {
      setInterval(() => {
        this.playWords = JSON.parse(JSON.stringify(this.words));
        demo(this.playWords);
      }, this.delay * (this.words.length + 2));
    }
  },
  methods: {}
};
</script>

<style lang="less" scoped>
* {
  color: #fff;
}
.center {
  text-align: center;
  color: #fff;
}
.app-box {
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
  bottom: 0;
  background: #000;
}
</style>



