Vue.component("search-bar", {
    template: `
    <div>
        <form class="form-inline">
            <input class="form-control" type="text" placeholder="Search" aria-label="Search">
            <button
                class="btn btn-outline-primary"
                type="submit"
            >
                Submit
            </button>
        </form>
    </div>
    `
});