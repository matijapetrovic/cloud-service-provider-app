Vue.component("users-page",{
    template: `
        <div> 
            <super-admin-page></super-admin-page>
            <!-- <admin-page></admin-page>-->     
        </div>
    `
    ,
    data: function(){
        return {
            users : null,
            user : null
        }
    },
    mounted () {
        axios.all([
            axios
            .post('api/login')
            .then(response => {
                this.user =  response.data
             }),
            axios
            .get('api/users')
            .then(response => {
                this.users = response.data
             })
        ])
       
         
       

            
    }

});