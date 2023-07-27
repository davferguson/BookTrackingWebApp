<template>
<div>
    <img class="homebackground" src="living-room.jpg">
    <NavBar/>
     <div class="profilewindow">
        <h3>Welcome to your Books!</h3>
    
        <div id="addbookform">
        <form v-show="addReadingActivity" >
            <input name="isbn" type="text" placeholder="Enter ISBN" v-model="addedBook.isbn"/>
            <input name="title" type="text" placeholder="Enter title" v-model="addedBook.book_name"/>
            <input name="author" type="text" placeholder="Enter author" v-model="addedBook.author"/>
            <input name="pages" type="number" placeholder="Enter number of pages" v-model="addedBook.numberofpages"/>
            <input name="format" type="text" placeholder="PAPER or DIGITAL?" v-model="addedBook.format"/>
            <br />
            <button class="addbook_btn" v-on:click.prevent="submitAddedBook()">Add Book</button>
        </form>
        </div>
        <!-- <BookInfo/> -->
        <BookList v-if="hasBooks" :books="$store.state.allBooks" :isAddBook='false' :isRemoveBook='true'/>
        
        <!-- <button class="addbook_btn" id="addbook" v-on:click="setReadingActivitytoTrue()">Add a Book!</button> -->
        <router-link class="addbook_btn" :to="{ name: 'addbook' }">Add a Book!</router-link>
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
            }
        }
},
    methods: {
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
            BookService.list(this.user).then(response => {
                this.$store.state.bookCompleted = response.data;
                if(response.data != ""){
                    this.hasBooks = true;
                }
            })
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