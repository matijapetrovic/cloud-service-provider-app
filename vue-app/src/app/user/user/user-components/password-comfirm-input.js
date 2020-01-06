Vue.component('password-confirm-input',{
    template: `
    <div
    v-bind:value="value"
    required="required"
    >
        <password-input
        name="password"
        >
        </password-input>
        
        <password-input
        name="confirmPasword"
        >
        </password-input>
    </div>
    `
    ,
    data(){
        return {
            password: null,
            confirm : null
        }
    },
    props:{
        value: null
    }
    
})