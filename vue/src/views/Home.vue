<template>
  <div>
    <img class="homebackground" src="living-room.jpg">
  <div class="home">
    <h1>Home</h1>
    <p>Welcome, {{username}}!</p>
  </div>
  <NavBar/>
  
  </div>
</template>

<script>
//import theFamily from '../views/Family.vue';
import NavBar from '@/components/NavBar.vue';

export default {
  name: "home",
  components: {
    //theFamily,
    NavBar
  },
  data() {
    return {
      username: this.$store.state.user.username,
    }
  }
};
</script>

<style>
   .homebackground {
      width: 90vw;
      height: 100vh;
      padding-left: 4%;
    } 
  .home {
    position:absolute;
    top:20px;
    left: 10%;
    color: #724E91;
    text-decoration: none;
    font-family: "Mouse Memoirs", sans-serif;
    font-size: 150%;
  }
  h1 {
    margin: 10px;
    margin-left: 0px;
  
  }


</style>