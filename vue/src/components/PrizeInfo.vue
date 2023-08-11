<template>
    <div class="info-container">
        <div>
            {{ prize.name }}
        </div>
        <div>
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

        }
    },
    methods: {  
        
    },
    created() {
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
#start-date {
    font-size: 1rem;
}
#end-date {
    font-size: 1rem;
}
</style>