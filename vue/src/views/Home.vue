<template>
  <div>
    <img class="homebackground" src="living-room.jpg">
  <div class="home">
    <h1>Home</h1>
    <p>Welcome, {{username}}!</p>
    <form>
      <input name="title" type="text" placeholder="Enter title" v-model="customSearch"/>
      <button v-on:click.prevent="searchTitle()">Search</button>
    </form>
    <p>Search: {{ search }}</p>
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
      customSearch: '',
      isbn: ''
    }
  },
  methods: {
    searchTitle() {
      const search = this.customSearch.split(' ')
      console.log(search)
      BookService.searchApiByTitle(search).then(response => {
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