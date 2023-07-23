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
    <p v-if="searchResult.books!=''">ISBN: {{ searchResult.books[0].isbn }}</p>
    <img v-if="searchResult.books!=''" v-bind:src="'http://covers.openlibrary.org/b/isbn/' + searchResult.books[0].isbn + '-M.jpg'" />

  </div>
  <NavBar/>
  
  </div>
</template>

<script>
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
      searchResult: {
        numFound: '',
        books: []
      }
    }
  },
  methods: {
    searchTitle() {
      const search = this.customSearch.split(' ')
      console.log(search)
      BookService.searchApiByTitle(search).then(response => {
      this.searchResult = response.data;
    })
    },
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