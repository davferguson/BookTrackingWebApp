<template>
<div>
    <img class="homebackground" src="living-room.jpg">
        <NavBar/>
         <div class="prizewindow">
             <h1 class="pageheading">Prizes coming soon!</h1>
        </div>
         <div class="prizebody">
             <p v-for="prize in this.$store.state.prizes" v-bind:key="prize.description">Prizes will be displayed here!</p>
            <!-- <p> {{ this.prize }} </p> -->
             <img id="trophy" src="trophy1.png">
             <img id="trophy" src="trophy1.png">
             <img id="trophy" src="trophy1.png">

        </div>
</div>
</template>   

<script>   
import PrizeService from '../services/PrizeService.js';
import NavBar from '@/components/NavBar.vue';

    export default {  
        components: {
            NavBar
         }, 
        data() {
            return {
            username: this.$store.state.user.username,
             }
        },

        created() {
            PrizeService.list().then( (response) => {
               this.$store.state.prizes = response.data; 
            })
        }
    };

</script>

<style scoped>
.prizebody {
    position: absolute;
    height: 30rem;
    width: 50%;
    top: 9rem;
    left: 28%;
    background-color: #22162b;
    margin: 0 auto;
    color: white;
    font-family: "Mouse Memoirs", sans-serif;
    font-size: 1.8em;
    text-align: center;
}

.prizewindow {
  text-align: center;
  position: absolute;
  top: 50px;
  left: 18%;
  background: rgba(114, 78, 145, 0.7);
  height: 40rem;
  width: 70vw;
}

.pageheading {
    font-family: "Mouse Memoirs", sans-serif;
    font-size: 4em;
    color: #F8C630;
    margin: 0;
}

#trophy {
    height: 15%;
}

</style>