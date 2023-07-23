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
import BookService from '../services/BookService';

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
  },
  created() {
    BookService.list(this.user).then(response => {
      this.$store.state.allBooks = response.data;
    })
  }
};
</script>

<style scoped>
  h1 {
    margin: 10px;
    margin-left: 0px;
  
  }


</style>