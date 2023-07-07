<template>
  <div>
    <img class="homebackground" src="living-room.jpg" />
    <div class="home">
      <p>Welcome, {{ this.username }}!</p>
    </div>
    <NavBar/>
    <div class="familywindow">
      <div>
      <h3 v-show="family.familyName == ''">Please register your Family...</h3>
      <h3 v-show="family.familyName != ''">The {{ family.familyName }} family</h3>
      </div>
      <reading-info v-for="member in this.$store.state.familyMembers"
      v-bind:key="member.user_id" v-bind:curUser="member"/>
      <div v-if="!isRegistered">
        <p id="RegisterFamily">Register your family!</p>
        <input
          type="text"
          placeholder="Family Name"
          v-model="family.familyName"
          v-show="disabled"
        />
        <input
          type="submit"
          v-on:click.prevent="registerFamily(), (disabled = false)"
          v-show="disabled"
        />
      </div>

      <div>
        <p id="AddChild">Add a child account</p>
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
          <input type="submit" v-on:click.prevent="registerChild()" />
        </form>
      </div>

      <div>
        <p id="AddFamily">Add a family member</p>
        <form>
          <input
            type="text"
            placeholder="Child Username"
            v-model="newFamilyMember.username"
          />
          <!-- <input type="text" placeholder="ID of Family" v-model="newFamilyMember.family_id"> -->
          <input type="submit" v-on:click.prevent="addFamilyMember()" />
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
      familyReading: "",
      registered: false,
      username: this.$store.state.user.username,
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
    ),

     FamilyService.getReadingActivity(this.$store.state.familyMembers).then((response) => {
        this.familyReading = response.data;
      })
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
      FamilyService.registerChild(this.child);
      this.child.username = "";
      this.child.password = "";
      this.child.confirmPassword = "";
        },
  

    registerFamily() {
      FamilyService.registerFamily(this.family).then((response) => {
        if (response.status === 201) {
          this.$store.state.familyMembers = response.data;
          this.registered = true;
        }
      });
    },

    addFamilyMember() {
      FamilyService.addToFamilyAccount(this.newFamilyMember).then(
        (response) => {
          if (response.status === 201) {
            this.$router.push("/");
          }
        }
      )
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

.familywindow {
  text-align: center;
  position: absolute;
  top: 50px;
  left: 18%;
  background: rgba(114, 78, 145, 0.7);
  height: 70vh;
  width: 70vw;
}

h3 {
  font-size: 40px;
  color: #f8c630;
  font-family: "Mouse Memoirs", sans-serif;
}


</style>