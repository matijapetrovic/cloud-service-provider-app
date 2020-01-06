Vue.component('password-confirm-input', {
    template: `
    <form id="demo" @submit.prevent="validateBeforeSubmit()">
  <div class="input-group">
    <div class="input-group-addon">
      Enter Password
    </div>

    <div class="input-fields">
      <input v-validate="'required'"
      name="password"
      type="password"
      class="form-control"
     placeholder="Password" ref="password">

      <input v-validate="'required|confirmed:password'"
       name="password_confirmation" 
       type="password" class="form-control"
        placeholder="Confirm password" data-vv-as="password">
    </div>
  </div>

  <div class="alert alert-danger" v-show="errors.any()">
    <div v-if="errors.has('password')">
      {{ errors.first('password') }}
    </div>
    <div v-if="errors.has('password_confirmation')">
      {{ errors.first('password_confirmation') }}
    </div>
  </div>

  <button type="submit" class="btn btn-primary">
        Validate!
    </button>
</form>

    `
    ,
    data() {
        return {
            errors: [],
            password: null,
            confirm: null
        }
    },
    props: {
        value: null,
        required: {
            type: Boolean,
            default: false
        }
    },
    methods: {
        validateBeforeSubmit: function () {
            if (this.password === this.confirm) {
                this.value = this.password;
                return true;
            }
            if (!this.password && this.confirm) {
                this.errors.push("Password is empty!");
            }
            else if (!this.confirm && this.password) {
                this.errors.push("Password is not confirmed!");
            }
            else if (this.password !== this.confirm) {
                this.errors.push("Your password and confirmation password do not match.");
            }

            e.preventDefault();
            return false;
        }
    }

})