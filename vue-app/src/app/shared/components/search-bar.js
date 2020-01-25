Vue.component("search-bar", {
    template: `
    <div>
        <form class="form-inline">
            <div class="input-group">
                <input
                v-bind:value="value"
                v-on:input="$emit('input', $event.target.value)"
                class="form-control" type="text" placeholder="Search" aria-label="Search">
                <button
                    class="btn btn-outline-primary"
                    v-on:click="$emit('search')" 
                >
                    Search
                </button>
            </div>
        </form> 
    </div>
    `,
    props: {
        value: ''
    }
});