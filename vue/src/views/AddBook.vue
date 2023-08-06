<template>
    <div>
      <img class="homebackground" src="living-room.jpg">
      <NavBar/>
        <div class="add_book_window">
            <h3>Search for books!</h3>
            <form id="filter_form">
                <input id="filter_input" name="search" type="text" placeholder="Enter title, author, or isbn" v-model="customSearch"/>
                <button class="addbook_btn" v-on:click.prevent="searchTitle()">Search</button>
                <router-link :to="{ name: 'readingList' }"><button class="addbook_btn">Back</button></router-link>
            </form>
            <BookList v-if="searchResult.books!=null" :books="searchResult.books" :isAddBook='true' :isRemoveBook='false'/>
        </div>
    
    
    </div>
  </template>
  <script>
  import NavBar from '@/components/NavBar.vue';
  import BookService from '../services/BookService';
  import BookList from '@/components/BookList.vue';
  
  export default {
    name: "home",
    components: {
      //theFamily,
      NavBar,
      BookList
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
      
    }
  };
  </script>
  
  <style scoped>
  h3 {
  font-size: 40px;
  color: #f8c630;
  background-color: #3a2649;
  padding-top: 7px;
  padding-bottom: 7px;
  margin: 0;
  font-family: "Mouse Memoirs", sans-serif;
}
.add_book_window {
  text-align: center;
  position:absolute;
  top:50px;
  left:18%;
  background: rgba(114,78,145,0.7);
  height:45rem;
  width: 70vw;
}
.addbook_btn {
  background-color: #22162B;
  color: #f8c630;
  font-family: "Mouse Memoirs", sans-serif;
  font-size: 1.8rem;
}
.addbook_btn:hover {
    background-color: #4a305e;
}
#filter_form {
    background-color: #3a2649;
}
#filter_input {
    height: 1.8rem;
    font-size: 1.8rem;
}
  
  </style>