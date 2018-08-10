window.appInstince = new Vue({
    el: '#app',
    data: {
        choseYear: new Date().getFullYear(),
        choseMonth: new Date().getMonth() + 1,
        markDate: [],
        monthEvent: [],
        historyEvent: [],
        years: []
    },
    computed: {

    },
    mounted() {
        this.between_year();
        this.loadHistory();
    },
    methods: {
        between_year() {
            let end = new Date().getFullYear();
            for (let start = end - 50; start <= end; start++) {
                this.years.push(start);
            }
        },
        choseday(dt) {
            console.log('chose day', arguments);
            this.historyEvent = this.monthEvent.filter(item => new Date(dt).getDate() == new Date(item.date).getDate());
        },
        handleData(dt) {
            this.monthEvent = dt;
            this.markDate = dt.map(item => item.date);
        },
        loadHistory() {
            let url = '/history/query';
            let data = {
                year: this.choseYear,
                month: this.choseMonth
            }
            ajax_promise(url, 'post', data).then(result => {
                this.handleData(result.data);
            });
        },
        dateTimeChange() {
            this.loadHistory();
            let datetime = this.choseYear+'-'+this.choseMonth;
            this.$refs.calendar.setDate(new Date(datetime));
        }
    }
});