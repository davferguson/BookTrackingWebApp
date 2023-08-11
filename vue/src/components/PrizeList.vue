<template>
    <div class="container">
        <PrizeInfo :prize="prize" v-for="prize in prizes" :key="prize.prize_id" />
    </div>
</template>

<script>
import PrizeInfo from '@/components/PrizeInfo.vue';
    
export default {
    name: 'prize-list',
    props: {
        prizes: Array,
    },
    components: {
        PrizeInfo
    },
}
</script>

<style scoped>
.container {

    display: grid;
    grid-auto-flow: row;
    margin-bottom: 0;
    padding-bottom: 0;
    height: 30rem;


    overflow-x: auto;
    overscroll-behavior-inline: contain;
}
</style>