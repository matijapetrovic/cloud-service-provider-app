Vue.component("users-page",{
    template: `

        <div> 
            <super-admin-page></super-admin-page>
            <!-- <admin-page></admin-page>-->     
        </div>

    <base-layout v-bind:page-title="$route.meta.title">
        <table border="1" class="table">
            <thead class="thead-dark">
                <tr>
                    <th>Email</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Organization</th>

                </tr>
            </thead>
            <tr v-for="user in users">
                <td><a href="#">{{ user.email }}</a></td>
                <td>{{ user.name }}</td>
                <td>{{ user.surname }}</td>
                <td>{{ user.organization }}</td>
            </tr>
        </table>
    </base-layout>
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