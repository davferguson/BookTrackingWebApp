<template>
  <div>
    <img class="homebackground" src="living-room.jpg" />
    <NavBar/>
    <div class="mainwindow">
      <div>
      <h3 v-show="family.familyName == ''">Please register your Family...</h3>
      <h3 v-show="family.familyName != ''">The {{ family.familyName }} family</h3>
      </div>
      <reading-info v-for="member in this.$store.state.familyMembers"
      v-bind:key="member.user_id" v-bind:curUser="member"/>
      <div v-if="!isRegistered&&isAdmin">
        <p id="RegisterFamily">Register your family!</p>
        <input
          type="text"
          placeholder="Family Name"
          v-model="family.familyName"
          v-show="disabled"
        />
        <br />
        <input
          type="submit"
          class="submit_btn"
          v-on:click.prevent="registerFamily(), (disabled = false)"
          v-show="disabled"
        />
      </div>

      <div v-if="isAdmin">
        <p id="AddChild">Create a child account</p>
        <form>
          <input
            type="text"
            placeholder="Child Username"
            v-model="child.username"
          />
          <br />
          <input type="text" placeholder="Password" v-model="child.password" />
          <br />
          <input
            type="text"
            placeholder="Confirm Password"
            v-model="child.confirmPassword"
          />
          <br />
          <input class="submit_btn" type="submit" v-on:click.prevent="registerChild()" />
          <div v-if="createChildError != ''" class="error-message">
            {{ createChildError }}
          </div>
        </form>
      </div>

      <div v-if="isAdmin">
        <p id="AddFamily">Add a family member</p>
        <form>
          <input
            type="text"
            placeholder="Username"
            v-model="newFamilyMember.username"
          />
          <br />
          <!-- <input type="text" placeholder="ID of Family" v-model="newFamilyMember.family_id"> -->
          <input class="submit_btn" type="submit" v-on:click.prevent="addFamilyMember()" />
          <div v-if="isError" class="error-message">
            {{ errorMessage }}
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import FamilyService from "../services/FamilyService";
import ReadingInfo from "../components/ReadingInfo.vue"
import NavBar from '@/components/NavBar.vue';

export default {
  name: "the-family",
  components: {
    ReadingInfo,
    NavBar
  },
  data() {
    return {
      registered: false,
      isAdmin: false,
      isError: false,
      username: this.$store.state.user.username,
      errorMessage: "",
      createChildError: "",
      family: {
        familyName: ""
      },
      child: {
        username: "",
        password: "",
        confirmPassword: "",
        role: "ROLE_USER",
      },
      disabled: true,
      newFamilyMember: {
        username: "",
      },
    };
  },
  props: ["id"],

  created() {
    FamilyService.getFamilyName(this.$store.state.user).then((response) => {
      this.family.familyName = response.data;
        FamilyService.list(this.$store.state.user).then((response) => {
          this.$store.state.familyMembers = response.data;
        });
      }
    );
    if(this.$store.state.user.authorities[0].name == 'ROLE_ADMIN'){
      this.isAdmin = true;
    }
  },

  computed:  {
    isRegistered() {
     if (this.$store.state.familyMembers.length != 0) {
      return true} else {
        return false
      }
  }},

  methods: {
    registerChild() {
      FamilyService.registerChild(this.child)
      .then((response) => {
        if(response.status === 201){
          this.createChildError = "";
          this.$router.push("/actioncompleted");
        }
      })
      .catch((error) => {
        this.createChildError = error.response.data.message;
      })
      .finally(() => {
        this.child.username = "";
        this.child.password = "";
        this.child.confirmPassword = "";
      })
    },
    registerFamily() {
      FamilyService.registerFamily(this.family)
      .then((response) => {
        if (response.status === 201) {
          this.$store.state.familyMembers = response.data;
          this.registered = true;
        }
      })
    },
    addFamilyMember() {
      FamilyService.addToFamilyAccount(this.newFamilyMember)
      .then((response) => {
          if (response.status === 201) {
            this.isError = false;
            this.errorMessage = "";
            this.$router.push("/actioncompleted");
          }
        })
      .catch((error) => {
          this.isError = true;
          this.errorMessage = error.response.data.message;
      })
    }
  }
};
</script>

<style>


#RegisterFamily,
#AddChild,
#AddFamily {
   color: #f8c630;
   font-size: 20px;
}
.submit_btn {
  width: 177px;
  background-color: #22162B;
  color: #f8c630;
  font-family: "Mouse Memoirs", sans-serif;
  font-size: 1.2rem;
}
.submit_btn:hover {
    background-color: #4a305e;
}
h3 {
  font-size: 40px;
  color: #f8c630;
  font-family: "Mouse Memoirs", sans-serif;
}


</style>