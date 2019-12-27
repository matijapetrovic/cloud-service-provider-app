Vue.component('profile-page',{
    template: `
        <div>
            Profile-info-form 
            Informacije profila
    
        </div>
    `  ,
    data: function(){
        return {
            user: null
        }
    },
    mounted () {
        axios
             .get('/users/profile')
             .then(response => {
                this.user = response.data
             })

             .post('api/login')
             .then(response => {
                 this.user =  response.data
             })
    },

});