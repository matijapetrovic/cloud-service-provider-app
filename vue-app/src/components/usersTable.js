Vue.component("users-table",{

    template: `
    <table border="1">
        <tr>
            <th>Email</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Organization</th>

        </tr>
    
        <tr v-for="user in users">
            <td><a href="#">{{ user.email }}</a></td>
            <td>{{ user.name}}</td>
            <td>{{ user.surname }}</td>
            <td>{{ user.organization }}</td>
        </tr>
    </table>
    `
,
data: function(){
    return {
        users: null
    }
},
mounted () {
    axios
        .get('/users')
        .then(response => {
            this.users = response.data
        })
}

})