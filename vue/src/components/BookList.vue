<template>
    <div>
    
        <div class="book-container">
            <BookInfo :isAddBook='isAddBook' :isRemoveBook='isRemoveBook' :book="book" v-for="book in books" :key="book.isbn" />
        </div>
    </div>
      
    </template>
    
    <script>
    import BookInfo from '@/components/BookInfo.vue';
    
    export default {
        name: 'book-list',
        props: {
            books: Array,
            isAddBook: Boolean,
            isRemoveBook: Boolean
        },
        components: {
            BookInfo
        },
    }
    </script>
    
    <style scoped>
    
    .book-container {
        /* background-color: #22162B; */
        display: grid;
        /* gap: 1%; */
        grid-auto-flow: row;
        margin-bottom: 0;
        padding-bottom: 0;
        height: 40rem;
    
    
        overflow-x: auto;
        overscroll-behavior-inline: contain;
        /* border: 10px solid #57386e;
        border-radius: 1px; */
        /* height: 100%; */
    
    }
    </style>