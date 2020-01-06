Vue.component("user-category-page",{
    template: `
    <div>
            <categories-from-organization-table
            @viewCategory="viewCategory($event)"
            v-bind:view-modal-id="viewModalId"
            ref="table"
            >
            </categories-from-organization-table>
            
            <full-modal
            @close="removeViewValidation"
            v-bind:modal-id="viewModalId"
            modal-title="View category"
            >
                <view-category-form
                    @submit="closeViewModal"
                    @updatedCategory="updateCategory($event)"
                    ref="viewForm"
                    >
                    </view-category-form>
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
        closeViewModal() {
            this.removeViewValidation();
            $('#' + this.viewModalId).modal('hide');
        },
        addCategory(category) {
            this.$refs.table.addCategory(category);
        },
        updateCategory(category) {
            this.$refs.table.updateCategory(category);
        },
        viewCategory(name) {
            this.$refs.viewForm.getCategory(name);
        }
    }
});