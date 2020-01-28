Vue.component("search-bar", {
    template: `
    <form class="form-inline">
        <div class="input-group">
            <input
            :value="value"
            @input="$emit('input', $event.target.value)"
            class="form-control" type="text" placeholder="Search" aria-label="Search">
            <button
                class="btn btn-outline-primary"
                @click="$emit('search')" 
            >
                Search
            </button>
        </div>
    </form> 
    `,
    props: {
        value: ''
    }
});