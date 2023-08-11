<template>
    <div class="info-container">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <div id="prize-name">
            {{ prize.name }}
        </div>
        <div id="prize-description">
            {{ prize.description }}
        </div>
        <div id="start-date">
            Start Date: 
            {{ prize.start_date }}
        </div>
        <div id="end-date">
            End Date: 
            {{ prize.end_date }}
        </div>
        <div id="progress-bar" class="w3-light-grey w3-round">
            <div ref="curBar" id="myBar" class="w3-container w3-round w3-green" style="height:24px;width:0%">
                <div>
                    {{ barWidth + '%' }}
                </div>
            </div>
        </div>
        <p v-if="prize.goal_type === 'minutes_read'"><span ref="minutesSpan">0</span> / <span ref="goalSpan">60</span> minutes read</p>
        <p v-if="prize.goal_type === 'books_finished'"><span ref="booksSpan">0</span> / <span ref="goalSpan">60</span> books finished</p>
    </div>
</template>

<script>
import ReadingService from '../services/ReadingService';
export default {
    name: 'prize-info',
    props: {
        prize: Object,
    },
    data() {
        return {
            barWidth: 0,
            readings: [],
            minutesRead: 0,
        }
    },
    methods: {  
        
    },
    mounted: function () {
        // let progressBarElem = this.$refs.curBar;
        // progressBarElem.style.width = this.barWidth + '%';
        // console.log("mounted: " + this.barWidth);
    },
    created() {
        let calculatedWidth = 0;
        ReadingService.selectReadingWithinDateTime(this.prize.start_date, this.prize.end_date, this.$store.state.user.username)
        .then((response) => {
            this.readings = response.data;
            this.readings.forEach((element) => {
                this.minutesRead += element.minutes_read;
            });
        
        if(this.prize.goal_type === "minutes_read"){
            calculatedWidth = Math.round((this.minutesRead / this.prize.goal_val)*100);
            if(calculatedWidth > 100){
                calculatedWidth = 100;
            } else if(calculatedWidth < 0){
                calculatedWidth = 0;
            }
            let minutesReadSpan = this.$refs.minutesSpan;
            minutesReadSpan.innerHTML = this.minutesRead;
        }
        
        let goalSpan = this.$refs.goalSpan;
        goalSpan.innerHTML = this.prize.goal_val;
        this.barWidth = calculatedWidth;
        let progressBarElem = this.$refs.curBar;
        progressBarElem.style.width = this.barWidth + '%';
        });
        
    },
    computed: {
    }
}
</script>

<style scoped>
#progress-bar {
    margin-left: 1rem;
    margin-right: 1rem;
}
.info-container {
    display: grid;
    background-color: #724e91b3;
    /* height: 5rem; */
    font-size: 1rem;
    border-top: 5px solid #3a2649;
    text-align: center;
}
.info-container > div {
    color: #f8c630;
    font-family: "Mouse Memoirs", sans-serif;
    font-size: 1.5rem;
}
#prize-name {
    font-size: 2rem;
}
#prize-description {
    font-size: 1.2rem;
}
#start-date {
    font-size: 1rem;
}
#end-date {
    font-size: 1rem;
}
</style>