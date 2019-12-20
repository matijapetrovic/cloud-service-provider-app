Vue.component("org-modal", {
    template: `
        <full-modal v-bind:modal-id="modalId" v-bind:modal-title="modalTitle">
            <add-org-form></add-org-form>
        </full-modal>
    `,
    props : {
        modalId : String,
        modalTitle: String
    }
});