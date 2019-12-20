Vue.component("full-modal", {
    template: `
    <div class="modal fade" v-bind:id="modalId" tabindex="-1">
        <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Add organization</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <slot></slot>
            </div>
        </div>
    </div>
    `,
    props: {
        modalId: String
    },

});