Vue.component('user-page',{
    template: `
        <div>
            <!-- Pokusaj prikaza na pritisnuto profile dugme-->
            <profile-page></profile-page>

            <button
            type="button"
            class="btn btn-outline-primary"
            data-toggle="modal"
            v-bind:data-target="'#' + updateModalId"
            style="margin: 15px 0;"
            >
                Change profiles
        </button>
        </div>
    `  ,
    data: function(){
        return {
            user: null
        }
    },
    mounted () {
        axios
             .get('api/users')
             .then(response => {
                this.user = response.data
             })

            .post('api/login')
            .then(response => {
                this.user =  response.data
            })
    },
    methods : {
        
    }


});