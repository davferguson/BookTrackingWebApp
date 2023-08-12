<template>
  <div class="card" :class="{ selected: isSelected }">
    <div id="expanded-full" v-if="isSelected">
        <div id="expanded-inner">
            <img class="expanded-img" :src="book.imageLink">
            <div class="expanded-center">
                <span class="close-button" @click="selectBook()">+</span>
                <p>Log Reading:</p>
                <p>Log Reading:</p>
            </div>
            
        </div>
    </div>
    <img :src="book.imageLink" v-on:click="selectBook()"/>
    <!-- <img @load="onImageLoad" v-on:click="selectBook()" v-if="book.isbn&&imageAvailable" v-bind:src="'http://covers.openlibrary.org/b/isbn/' + book.isbn + '-M.jpg'" />
    <div v-on:click="selectBook()" id="nocover" v-else >{{ book.book_name }}<p>{{ book.author }}</p></div> -->
    <div v-on:click="selectBook()" v-bind:class="{ 'color-overlay': isSelected }"></div>
    <p class="centered">Log Reading:</p>
    <form v-show="isSelected" class="centered">
        <input placeholder="Minutes" min="1" id="time-read" name="timeRead" type="number" v-model="readingActivity.minutes_read"/><br>
        <input id="submit" v-on:click.prevent="submitReadingInfo()" type="submit"/>
        <div v-if="!isValidData" class="error-message">invalid data</div>
    </form>
  </div>
</template>

<script>
import BookService from '../services/BookService';
import FamilyService from '../services/FamilyService';

export default {
    name: 'book-card',
    props: {
        book: Object,
    },
    data() {
        return {
            url: "/book/" + this.book.isbn,
            user: this.$store.state.user,
            imageAvailable: true,
            isValidData: true,
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
        selectBook(){
            if(this.$store.state.selectedBook != this.book.isbn){
                this.$store.commit('SET_SELECTED_BOOK', this.book.isbn)
            }
            else{
                this.$store.commit('SET_SELECTED_BOOK', '')
            }
            this.$store.commit('SET_CURRENTLY_READING_SELECTED_BOOK', '0')
            
        },
        submitReadingInfo() {
            if(this.readingActivity.minutes_read > 0){
                this.isValidData = true;
                BookService.submitReading(this.readingActivity).then( response => {
                    if (response.status === 201) {
                        // this.$router.push('/actioncompleted')
                    }
                    BookService.listCurrent(this.user).then(response => {
                        this.$store.commit('SET_CURRENTLY_READING', response.data)
                    })
                    BookService.listCompleted(this.user).then(response => {
                        this.$store.commit('SET_FINISHED_READING', response.data)
                    })
                });
                this.$store.commit('SET_CURRENTLY_READING_SELECTED_BOOK', '0');
                FamilyService.getReadingActivityChild(this.user).then(response => {
                    this.$store.commit('SET_TOTAL_MINUTES_READ', response.data.minutes_read); 
                }),
                BookService.minutesRead(this.readingActivity.username, this.readingActivity.isbn).then(response => {
                    // this.minutes_read = response.data;
                    // this.totalMinutes = response.data;
                    this.$store.commit('SET_CUR_BOOK_MINUTES_READ', response.data)
                });
            } else{
                this.isValidData = false;
            }
            
        }
        
    },
    computed: {
        isSelected(){
            return this.$store.state.selectedBook == this.book.isbn;
        }
    }
}
</script>

<style scoped>
.close-button {
  position: absolute;
  right: 10px;
  top: -10px;
  font-size: 55px;
  font-weight: 600;
  color:#ffffff;
  opacity: 0.5;
  display: inline-block;
  transform: rotate(45deg);
}
.close-button:hover {
  opacity: 1;
  cursor: pointer;
}
#expanded-full {
    position: fixed;
    background: rgba(0, 0, 0, 0.747);
    height: 100vh;
    width: 100vw;
    top: 0;
    left: 0;
    z-index: 1;
}
#expanded-inner {
    display: inline-block;
    height: 50%;
    transform: translate(0, 50%);
}
.expanded-center {
    position: relative;
    top: -100%;
}
.expanded-img {
    height: 100%;
    background: #000;
}
.error-message {
    color: red;
}
p {
    color: white;
    font-size: 1.1rem;
    margin: 1px;
}

#nocover {
    height: 98%;
    font-size: 1.5rem;
    color: white;
}

#time-read {
    width: 5rem;
}

#submit {
    width: 5.5rem;
}

label {
    color: white;
    padding-bottom: 2rem;

}

#time_read {
    width: 6rem
}

.color-overlay {
    /* width: 100%; */
    height: 100%;
    background: #000;
    opacity: .7;
    position: relative;
    top: -101%;
    /* z-index: 1; */
}
.centered {
    position: relative;
    top: -155%;
    left: 0px;
}
.card {
    height: 12rem;
    background-color: #3f2950;
}
.card.selected {
    background-color: rgba(211,211,211,0.1);
    border: 2px solid white;
    border-radius: 1px;
}
img {
    height: 98%;
    /* height: 14rem; */
}
</style>