<template>
    <div class="container">
        <div class="img">
            <img @load="onImageLoad" v-if="imageAvailable" id="cover_image" v-bind:src="'http://covers.openlibrary.org/b/isbn/' + book.isbn + '-M.jpg'"/>
            <img v-if="!imageAvailable" src="No-Image-Placeholder.png"/>
        </div>
        <div class="info">
            {{ book.book_name }}<br>
            Author: {{ book.author }}<br>
            ISBN: {{ book.isbn }}<br>
            <button v-if="isAddBook" class="addbook_btn" v-on:click.prevent="addBook()">Add Book</button>
            <button v-if="isRemoveBook" class="addbook_btn" v-on:click.prevent="removeBook()">Remove Book</button>
        </div>
    </div>
</template>

<script>
import BookService from '../services/BookService';

export default {
    name: 'book-info',
    props: {
        book: Object,
        isAddBook: Boolean,
        isRemoveBook: Boolean
    },
    data() {
        return {
            url: "/book/" + this.book.isbn,
            user: this.$store.state.user,
            minutes_read: 0,
            imageAvailable: true,
            readingActivity: {
              username: this.$store.state.user.username, 
              minutes_read: "",
              isbn: this.book.isbn
            }

        }
    },
    methods: {  
        onImageLoad() {
            const curImg = new Image();
            curImg.src = 'http://covers.openlibrary.org/b/isbn/' + this.book.isbn + '-M.jpg';
            if(curImg.height == 1){
                this.imageAvailable = false;
            }
        },
        removeBook(){
            BookService
            .removeBook(this.book).then(response => {
                if (response.status === 200) {
                    this.$router.push('/actioncompleted')
                }
            })
        },
        addBook() {
            BookService
            .submitBook(this.book).then(response => {

                if (response.status === 201) {
                    this.$router.push('/actioncompleted')
                }
        })},
    },
    created() {
        BookService.minutesRead(this.readingActivity.username, this.readingActivity.isbn).then(response => {
                this.minutes_read = response.data;
            });
    },
    computed: {
    }
}
</script>

<style scoped>
.container {
  display: grid;
  /* grid-template-areas: 
  'image info info info info'; */
  grid-template-columns: 8.3rem 1fr;
  gap:0px;
  background-color: #724e91b3;
  padding: 0px;
}
.container > div {
  border-top: 5px solid #3a2649;

}
img {
    max-width: 8rem;
}
.img {
    /* grid-area: image; */
    border-right: 5px solid #3a2649;
    /* background-color: #3a2649; */
    background-color: #3f2950;
    /* width: 10rem; */
}
.info {
    /* grid-area: info; */
    color: #f8c630;
    font-family: "Mouse Memoirs", sans-serif;
    text-align: center;
    font-size: 1.5rem;
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
</style>