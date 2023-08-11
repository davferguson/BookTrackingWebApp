<template>
<div>
    <img class="homebackground" src="living-room.jpg">
    <NavBar/>
     <div class="profilewindow">
        <h3>Welcome to your Books!</h3>
        <form id="filter_form">
            <input id="filter_input" name="filter" type="text" placeholder="Enter title" v-model="searchFilter"/>
            <button class="addbook_btn" v-on:click.prevent="filterBooks()">Filter books</button>
            <button class="addbook_btn" v-on:click.prevent="clearFilter()">Clear</button>
            <button class="addbook_btn" v-on:click.prevent="addBook()">Add a Book!</button>
        </form>
        <BookList v-if="hasBooks" :books="filteredBooks" :isAddBook='false' :isRemoveBook='true'/>
        <!-- <router-link class="addbook_btn" :to="{ name: 'addbook' }">Add a Book!</router-link> -->
    </div>

 
</div>
   
</template>

<script>
import BookService from '../services/BookService';
import NavBar from '@/components/NavBar.vue';
// import BookInfo from '@/components/BookInfo.vue';
import BookList from '@/components/BookList.vue';
export default {
    
    name: 'reading-list',
    components: {
    NavBar,
    // BookInfo
    BookList
  },
  

    data() {
        return {
            username: this.$store.state.user.username,
            addReadingActivity: false,
            hasBooks: false,
            addedBook: {
                book_name: "",
                isbn: "",
                author: "",
                numberofpages: "",
                format: ""
            },
            allBooks: this.$store.state.allBooks,
            // allBooks: [{book_name: 'test'},{book_name: 'The'},{book_name: 'The'},],
            filteredBooks: this.$store.state.allBooks,
            searchFilter: '',
        }
},
    methods: {
        addBook() {
            this.$router.push('/addbook')
        },
        filterBooks() {
            let vm = this;
            this.filteredBooks = this.allBooks.filter(function (book) { return book.book_name.toUpperCase().includes(vm.searchFilter.toUpperCase()); });
        },
        clearFilter() {
            this.filteredBooks = this.allBooks;
            this.searchFilter = '';
        },
        setReadingActivitytoTrue() {
            this.addReadingActivity = true;
        },
        submitAddedBook() {
            BookService
            .submitBook(this.addedBook).then(response => {

                if (response.status === 201) {
                    this.$router.push('/actioncompleted')
                }
        })},
    
    },
    created() {
            BookService.list(this.$store.state.user).then(response => {
                this.$store.commit('SET_ALL_BOOKS', response.data);
                if(response.data != ""){
                    this.hasBooks = true;
                }
            });
        }
}

</script>


<style scoped>
.addbook_btn {
  background-color: #22162B;
  color: #f8c630;
  font-family: "Mouse Memoirs", sans-serif;
  font-size: 1.8rem;
}
.addbook_btn:hover {
    background-color: #4a305e;
}
#filter_input {
    height: 1.8rem;
    font-size: 1.8rem;
}
#filter_form {
    background-color: #22162B;
}
.bookAdded {
    height:40px;
    width: 50%;
    background: rgba(255, 253, 253, 0.7);
    position: relative;
    top: 175px;
    left: auto;
    margin: 0 auto;
    color:rgb(56, 53, 53);
    font-size: 20px;
    font-family: "Mouse Memoirs", sans-serif;
    border-radius: 15px;
    border: solid;
    border-color: white;
    margin-bottom: 4px;
}

.profilewindow {
    text-align: center;
    position:absolute;
    top:50px;
    left:18%;
    /* background: rgba(114,78,145,0.7); */
    height:45rem;
    width: 70vw;
}
h3 {
  font-size: 40px;
  color: #f8c630;
  background-color: #3a2649;
  padding-top: 7px;
  padding-bottom: 7px;
  margin: 0;
  font-family: "Mouse Memoirs", sans-serif;
}

#addbookform {
    position: absolute;
    top: 125px;
    left: 20px
}

#addbook {
    
    left: 400px;
}

input[name="isbn"] {
    height: 50px;
    font-size: 20px;
}

input[name="title"] {
    height: 50px;
    font-size: 20px;
}

input[name="author"] {
    height: 50px;
    font-size: 20px;
}

input[name="pages"] {
    height: 50px;
    font-size: 20px;
}

input[name="format"] {
    height: 50px;
    font-size: 20px;
}
</style>