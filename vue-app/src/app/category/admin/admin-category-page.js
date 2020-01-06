Vue.component("admin-category-page",{
    template: `
    <div>
        
        <categories-from-organization-table
        @viewCategory="viewCategory($event)"
        v-bind:view-modal-id="viewModalId"
        ref="table"
        >
        </categories-from-organization-table>

        <button
        type="button"
        class="btn btn-outline-primary"
        data-toggle="modal"
        v-bind:data-target="'#' + addModalId"
        style="margin: 15px 0;"
        >
            Add category
        </button>
        
        <full-modal
        @close="removeViewValidation"
        v-bind:modal-id="viewModalId"
        modal-title="View category"
        >
            <view-category-form
                @submit="closeViewModal"
                @submitDelete="closeViewModal"
                @updatedCategory="updateCategory($event)"
                @deletedCategory="deleteCategory($event)"
                ref="viewForm"
                >
                </view-category-form>
        </full-modal>

        <full-modal
        @close="removeAddValidation"
        v-bind:modal-id="addModalId"
        modal-title="Add category"
        >
            <add-category-form
                @submit="closeAddModal"
                @addedCategory="addCategory($event)"
                ref="addForm"
                >
                </add-category-form>
        </full-modal>        
    </div>
`
,
data : function() {
    return {
        addModalId: 'addCategoryModal',
        viewModalId: 'viewCategoryModal'
    }
},
methods: {
    removeViewValidation() {
        this.$refs.viewForm.$refs.form.removeValidation();
    },
    removeAddValidation () {
        this.$refs.addForm.$refs.form.removeValidation();
    },
    closeViewModal() {
        this.removeViewValidation();
        $('#' + this.viewModalId).modal('hide');
    },
    closeAddModal() {
        this.removeAddValidation();
        $('#' + this.addModalId).modal('hide');
    },
    addCategory(category) {
        this.$refs.table.addCategory(category);
    },
    updateCategory(category) {
        this.$refs.table.updateCategory(category);
    },
    deleteCategory(category){
        this.$refs.table.deleteCategory(category);
    },
    viewCategory(name) {
        this.$refs.viewForm.getCategory(name);
    }
}
});