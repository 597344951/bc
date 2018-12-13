import Timeline from './src/timeline.vue'

if (typeof window !== 'undefined' && window.Vue) {
  Vue.component(Timeline.name, Timeline)
  window.app = new Vue({
    el: '#app',
    data: {
      events: [
        {
          year: 2008,
          eventList: [
            {
              month: 2,
              day: 13,
              eventList: [
                {
                  name: 'Philadelphia Museum School',
                  startTime: '2016-02-13 10:00:00',
                  endTime: '2016-02-13 11:00:00',
                  place: '第三试验场地',
                  description:
                    "Attends the Philadelphia Museum School of Industrial Art. Studies design with Alexey Brodovitch, art director at Harper's Bazaar, and works as his assistant."
                }
              ]
            },
            {
              month: 3,
              day: 1,
              eventList: [
                {
                  name: 'Philadelphia Museum School',
                  startTime: '2016-02-13 10:00:00',
                  endTime: '2016-02-13 11:00:00',
                  place: '第三试验场地',
                  description:
                    "Attends the Philadelphia Museum School of Industrial Art. Studies design with Alexey Brodovitch, art director at Harper's Bazaar, and works as his assistant."
                },
                {
                  name: 'Philadelphia Museum School',
                  startTime: '2016-02-13 10:00:00',
                  endTime: '2016-02-13 11:00:00',
                  place: '第三试验场地',
                  description:
                    "Attends the Philadelphia Museum School of Industrial Art. Studies design with Alexey Brodovitch, art director at Harper's Bazaar, and works as his assistant."
                }
              ]
            }
          ]
        }
      ]
    },
    methods: {
      onChoose(detail) {
        console.log(detail)
      },
      onEdit(detail) {
        console.log(detail)
      },
      onDelete(detail) {
        console.log(detail)
      }
    }
  })
}

export default Timeline
