<script type="text/babel">
export default {
  info: {
    name: "dangshi",
    author: "Wangch",
    descript: "党史"
  },
  props: {},
  data() {
    return {
      visible: true
    };
  },
  methods: {},
  computed: {}
};
</script>
<template>
   <div class="bg-box">
		<div class="content">
			<div class="ding">
				<img class="ding1" src="./images/ding.png" alt="">
				<img class="ding2" src="./images/ding.png" alt="">
			</div>
			<div class="left-box">
				<div class="main-content">
					<h1>中央军委向武警部队授旗仪式在北京举行习近平向武警部队授旗并致训词</h1>
					<img src="./images/1.jpg" alt="">
					<p>一月十日，中央军委向武警部队授旗仪式在北京八一大楼举行。中共中央总书记、国家主席、中央军委主席习近平向武警部队授旗并致训词。这是习近平致训词。</p>
				</div>
			</div>
			<div class="calender-box">
				<div class="date-box">
					<div class="select">
						<span>2018年</span>
						<div class="year-list">
							<ul>
								<li>1921年</li>
								<li>1922年</li>
								<li>1923年</li>
								<li>1924年</li>
								<li>1925年</li>
								<li>1926年</li>
								<li>1927年</li>
								<li>1928年</li>
								<li>1929年</li>
								<li>1930年</li>
								<li>1931年</li>
								<li>1932年</li>
								<li>1933年</li>
								<li>1934年</li>
								<li>1935年</li>
								<li>1936年</li>
								<li>1937年</li>
								<li>1938年</li>
								<li>1939年</li>
								<li>194年</li>
								<li>1941年</li>
								<li>1942年</li>
								<li>1943年</li>
								<li>1944年</li>
								<li>1945年</li>
								<li>1946年</li>
								<li>1947年</li>
								<li>1948年</li>
								<li>1949年</li>
								<li>1950年</li>
								<li>1951年</li>
								<li>1952年</li>
								<li>1953年</li>
								<li>1954年</li>
								<li>1955年</li>
								<li>1956年</li>
								<li>1957年</li>
							</ul>
						</div>
					</div>
					
					<div class="select">
						<span>--选择月--</span>
						<div class="month-list">
							<ul>
								<li><a>1月</a> <span></span></li>
								<li>2月</li>
								<li>3月</li>
								<li>4月</li>
								<li>5月</li>
								<li>6月</li>
								<li>7月</li>
								<li>8月</li>
								<li>9月</li>
								<li>10月</li>
								<li>11月</li>
								<li>12月</li>
							</ul>
						</div>
					</div>
					
				</div>
				<div class="date-list">
					<table border="0" cellspacing="1" cellpadding="0">
						<thead>
							<tr>
								<th>一</th>
								<th>二</th>
								<th>三</th>
								<th>四</th>
								<th>五</th>
								<th>六</th>
								<th>日</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td>2</td>
								<td>3</td>
								<td>4</td>
								<td>5</td>
								<td>6</td>
								<td>7</td>
							</tr>
							<tr>
								<td class="today">8</td>
								<td>9</td>
								<td>10</td>
								<td>11</td>
								<td>12</td>
								<td>13</td>
								<td>14</td>
							</tr>
							<tr>
								<td>15</td>
								<td>16</td>
								<td>17</td>
								<td>18</td>
								<td>19</td>
								<td>20</td>
								<td>21</td>
							</tr>
							<tr>
								<td class="active">22</td>
								<td>23</td>
								<td>24</td>
								<td>25</td>
								<td>26</td>
								<td>27</td>
								<td>28</td>
							</tr>
							<tr>
								<td>29</td>
								<td>30</td>
								<td>31</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</template>
<style scoped>
img {
  display: block;
}
ul,
li {
  list-style: none;
  margin: 0;
  padding: 0;
}
/* 设置滚动条的样式 */
::-webkit-scrollbar {
  width: 5px;
}
/* 滚动槽 */
::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 6px #ddd;
  border-radius: 3px;
}
/* 滚动条滑块 */
::-webkit-scrollbar-thumb {
  border-radius: 3px;
  background: #7d0000;
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.5);
}
::-webkit-scrollbar-thumb:window-inactive {
  /*失去焦点 颜色*/
  background: rgba(255, 0, 0, 0.4);
}

::-webkit-scrollbar {
  height: 5px;
  width: 5px;
}
::-webkit-scrollbar-button {
  height: 0;
  width: 0;
}
.bg-box {
  width: 100%;
  height: 100%;
  background: url(./images/bg.jpg) no-repeat;
  background-size: cover;
  min-width: 1210px;
  min-height: 682px;
}
.content {
  width: 90%;
  height: 98%;
  margin: 0 auto;
  background: url(./images/content.png) no-repeat;
  -webkit-background-size: contain;
  background-size: contain;
  background-position: 0px 30px;
	position: relative;
	min-height: 910px;
}
.ding {
  height: 70%;
  width: 64px;
  position: absolute;
  left: 50%;
  margin-left: -32px;
  top: 100px;
}
.ding1 {
  position: absolute;
  top: 0;
}
.ding2 {
  position: absolute;
  bottom: 0;
}
.left-box {
  width: 43%;
  height: 80%;
  position: absolute;
  bottom: 84px;
  left: 46px;
  box-shadow: inset 0 0 15px 2px #ccc;
}
.calender-box {
  width: 40%;
  height: 60%;
  position: absolute;
  bottom: 84px;
  right: 86px;
  overflow: auto;
}
.main-content {
  width: 100%;
  height: 100%;
  padding: 0px 20px;
  box-sizing: border-box;
}
.main-content > img {
  margin: 0 auto;
  width: 90%;
}
.main-content > p {
  text-indent: 2em;
}
.date-box {
  float: right;
  margin: 5px;
  width: 100%;
  text-align: right;
}
.date-box > .select {
  width: 100px;
  height: 30px;
  cursor: pointer;
  position: relative;
  background-color: rgba(255, 255, 255, 1);
  margin-left: 5px;
  display: inline-block;
  border: 1px solid #cd3333;
}
.select > span {
  display: inline-block;
  width: 100%;
  text-align: center;
  line-height: 30px;
  font-size: 14px;
}
.year-list,
.month-list {
  position: absolute;
  left: 0;
  top: 30px;
  height: 350px;
  overflow: auto;
  display: none;
}
.year-list li,
.month-list li {
  width: 100px;
  height: 28px;
  background-color: rgba(255, 255, 255, 1);
  margin-top: 1px;
  text-align: center;
  font-size: 13px;
  line-height: 28px;
  position: relative;
}
.year-list li:hover,
.month-list li:hover {
  background-color: rgba(255, 255, 255, 0.8);
}
.date-list {
  margin: 40px auto 0;
}
.date-list th,
td {
  width: 60px;
  height: 50px;
  box-sizing: border-box;
  margin: 0;
  padding: 0;
  border: none;
}
.date-list th {
  background-color: rgba(255, 255, 255, 0.8);
  color: #980000;
}
.date-list td {
  background-color: rgba(255, 255, 255, 0.2);
  text-align: center;
  cursor: pointer;
}
.date-list td.today {
  background-color: #fff;
}
.date-list td.active {
  background-color: rgba(255, 255, 255, 0.05);
  position: relative;
  color: #fff;
}
.date-list td.active:before {
  content: "";
  width: 0;
  height: 0;
  border: 10px solid transparent;
  border-right-color: #fff;
  position: absolute;
  top: -10px;
  right: -10px;
  transform: rotate(135deg);
}
</style>