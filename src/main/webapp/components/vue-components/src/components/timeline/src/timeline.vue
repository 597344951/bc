<template>
  <div class="timeline">
    <div class="timeline__group" v-for="y in events" :key="y.year">
      <span class="timeline__year">{{y.year}}</span>
      <div class="timeline__box" v-for="d in y.eventList" :key="y.year + '-' + d.month + '-' + d.day">
        <div class="timeline__date">
          <span class="timeline__day">{{d.day > 9 ? d.day : '0' + d.day}}</span>
          <span class="timeline__month">{{d.month > 9 ? d.month : '0' + d.month}}</span>
        </div>
        <div class="timeline__post">
          <div class="timeline__content" v-for="e in d.eventList" :key="e.period">

            <h3><span @click="onChoose(y.year, d.month, d.day, e)">{{e.name}}</span>
              <div class="timeline_btn">
                <button class="edit" @click="onEdit(y.year, d.month, d.day, e)">编辑</button>
                <button class="delete" @click="onDelete(y.year, d.month, d.day, e)">移除</button>
              </div>
            </h3>
            <h4>{{new Date(e.startTime).toLocaleString()}} - {{new Date(e.endTime).toLocaleString()}} {{e.place}}</h4>
            <p>{{e.description}}</p>

          </div>
        </div>
      </div>
    </div>

  </div>
</template>
<script>
export default {
  name: 'timeline',
  label: '时间轴',
  props: ['events'],
  methods: {
    onChoose(year, month, day, event) {
      this.$emit('on-choose', {
        year: year,
        month: month,
        day: day,
        event: event
      })
    },
    onEdit(year, month, day, event) {
      this.$emit('on-edit', {
        year: year,
        month: month,
        day: day,
        event: event
      })
    },
    onDelete(year, month, day, event) {
      this.$emit('on-delete', {
        year: year,
        month: month,
        day: day,
        event: event
      })
    }
  }
}
</script>
<style scoped>
.timeline {
  --uiTimelineMainColor: var(--timelineMainColor, #222);
  --uiTimelineSecondaryColor: var(--timelineSecondaryColor, #fff);

  position: relative;
  padding-top: 3rem;
  padding-bottom: 3rem;
}

.timeline:before {
  content: '';
  width: 4px;
  height: 100%;
  background-color: var(--uiTimelineMainColor);

  position: absolute;
  top: 0;
}

.timeline__group {
  position: relative;
}

.timeline__group:not(:first-of-type) {
  margin-top: 4rem;
}

.timeline__year {
  padding: 0.5rem 1.5rem;
  color: var(--uiTimelineSecondaryColor);
  /* background-color: var(--uiTimelineMainColor); */
  background-color: #131e5e;

  position: absolute;
  left: 0;
  top: 0;
}

.timeline__box {
  position: relative;
}

.timeline__box:not(:last-of-type) {
  margin-bottom: 30px;
}

.timeline__box:before {
  content: '';
  width: 100%;
  height: 2px;
  background-color: var(--uiTimelineMainColor);

  position: absolute;
  left: 0;
  z-index: -1;
}

.timeline__date {
  min-width: 70px;
  position: absolute;
  left: 0;

  box-sizing: border-box;
  padding: 0.5rem 0;
  text-align: center;

  background-color: var(--uiTimelineMainColor);
  color: var(--uiTimelineSecondaryColor);
}

.timeline__day {
  font-size: 2rem;
  font-weight: 700;
  display: block;
}

.timeline__month {
  display: block;
  font-size: 0.8em;
  text-transform: uppercase;
}

.timeline__post {
  padding: 1.5rem 2rem;
  border-radius: 2px;
  border-left: 3px solid var(--uiTimelineMainColor);
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.12), 0 1px 2px 0 rgba(0, 0, 0, 0.24);
  background-color: var(--uiTimelineSecondaryColor);
  margin-left: 2rem;
}

.timeline:before {
  left: 30px;
}

.timeline__group {
  padding-top: 55px;
}

.timeline__box {
  padding-left: 80px;
}

.timeline__box:before {
  top: 50%;
  transform: translateY(-50%);
}

.timeline__date {
  top: 50%;
  margin-top: -27px;
}

.timeline {
  --timelineMainColor: #4557bb;
}

.timeline__content h3 span {
  cursor: pointer;
  color: #5565c0;
}
.timeline__content h4 {
  color: #7888dd;
}
.timeline_btn {
  float: right;
  margin-right: 15px;
}
.timeline_btn button {
  width: 45px;
  height: 25px;
  border: none;
  outline: none;
  border-radius: 4px;
  color: #fff;
  cursor: pointer;
  font-size: 12px;
}
button.edit {
  background-color: cornflowerblue;
}
button.edit:hover {
  background-color: rgb(66, 113, 199);
}
button.delete {
  background-color: crimson;
}
button.delete:hover {
  background-color: rgb(182, 6, 41);
}
</style>
