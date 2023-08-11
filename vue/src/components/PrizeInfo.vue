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
        <div id="progress-bar" class="w3-light-grey">
            <div ref="curBar" id="myBar" class="w3-container w3-green" style="height:24px;width:0%">
            </div>
        </div>
    </div>
</template>

<script>

export default {
    name: 'prize-info',
    props: {
        prize: Object,
    },
    data() {
        return {
            barWidth: 0,
        }
    },
    methods: {  
        
    },
    mounted: function () {
        let progressBarElem = this.$refs.curBar;
        progressBarElem.style.width = this.barWidth + '%';
        console.log("mounted: " + this.barWidth);
    },
    created() {
        this.barWidth = Math.floor(Math.random() * 100);
    },
    computed: {
    }
}
</script>

<style scoped>
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