<template>
  <div>
    <img class="homebackground" src="living-room.jpg">
  <div class="home">
    <h1>Home</h1>
    <p>Welcome, {{username}}!</p>
    <form>
      <input name="title" type="text" placeholder="Enter title" v-model="search"/>
      <br />
      <button v-on:click.prevent="searchTitle()">Search</button>
    </form>
    <p>ISBM: {{ isbn }}</p>
    <img v-if="isbn" v-bind:src="'http://covers.openlibrary.org/b/isbn/' + isbn + '-M.jpg'" />
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
      search: ["plainsong"],
      isbn: ''
    }
  },
  methods: {
    searchTitle() {
      BookService.searchApiByTitle(this.search).then(response => {
      this.isbn = response.data[1];
    })
    },
  },
  created() {
    BookService.list(this.user).then(response => {
      this.$store.state.allBooks = response.data;
    }),
    BookService.searchApiByTitle(this.search).then(response => {
      this.isbn = response.data[1];
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