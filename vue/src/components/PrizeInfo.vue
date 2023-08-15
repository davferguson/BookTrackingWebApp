<template>
    <div class="info-container">
        <!-- <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css"> -->
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
import BookService from '../services/BookService';
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
            booksFinished: 0,
        }
    },
    methods: {  
        
    },
    mounted: function () {
        // let progressBarElem = this.$refs.curBar;
        // progressBarElem.style.width = this.barWidth + '%';
        // console.log("mounted: " + this.barWidth);
        let goalSpan = this.$refs.goalSpan;
        goalSpan.innerHTML = this.prize.goal_val;
    },
    created() {
        if(this.prize.goal_type === "minutes_read"){
            ReadingService.selectReadingWithinDateTime(this.prize.start_date, this.prize.end_date, this.$store.state.user.username)
            .then((response) => {
                this.readings = response.data;
                this.readings.forEach((element) => {
                    this.minutesRead += element.minutes_read;
                });
                
                let calculatedWidth = 0;
                calculatedWidth = Math.round((this.minutesRead / this.prize.goal_val)*100);
                if(calculatedWidth > 100){
                    calculatedWidth = 100;
                } else if(calculatedWidth < 0){
                    calculatedWidth = 0;
                }
                let minutesReadSpan = this.$refs.minutesSpan;
                if(minutesReadSpan != null){
                    minutesReadSpan.innerHTML = this.minutesRead;
                }
                
                this.barWidth = calculatedWidth;
                let progressBarElem = this.$refs.curBar;
                progressBarElem.style.width = this.barWidth + '%';
            });
        }
        if(this.prize.goal_type === "books_finished"){
            BookService.selectBooksWithinDateTime(this.prize.start_date, this.prize.end_date, this.$store.state.user.username)
            .then((response) => {
                response.data.forEach(() => {
                    this.booksFinished++;
                });

                let calculatedWidth  = Math.round((this.booksFinished / this.prize.goal_val)*100);
                if(calculatedWidth > 100){
                    calculatedWidth = 100;
                } else if(calculatedWidth < 0){
                    calculatedWidth = 0;
                }

                let booksFinishedSpan = this.$refs.booksSpan;
                if(booksFinishedSpan != null){
                    booksFinishedSpan.innerHTML = this.booksFinished;
                }

                this.barWidth = calculatedWidth;
                let progressBarElem = this.$refs.curBar;
                progressBarElem.style.width = this.barWidth + '%';
            });
        }
    },
    computed: {
    }
}
</script>

<style scoped>
.w3-light-grey,.w3-hover-light-grey:hover,.w3-light-gray,.w3-hover-light-gray:hover{
    color:#000!important;
    background-color:#f1f1f1!important

}
.w3-round{
    border-radius:4px
}
.w3-container{
    /* padding:0.01em 0px */
    min-width: 6%;
}

.w3-green{
    color:#fff!important;
    background-color:#4CAF50!important
}
#progress-bar {
    margin-left: 1rem;
    margin-right: 1rem;
}
.info-container {
    display: flexbox;
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