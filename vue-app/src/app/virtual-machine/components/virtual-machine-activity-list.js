Vue.component("vm-activity-list", {
    template: `
    <div>
        <label class="typo__label">Activity list</label>
        <multiselect 
            v-model="selectedVal" 
            :options="options"
            :searchable="false"
            :custom-label="nameWithLang"
            :close-on-select="true"
            label="startDate"
            :show-labels="false"
            track-by="startDate"
        >
        </multiselect>
    </div>
    `,
    props: {
        value: Object,
        options: {
            type: Array
        }
    },
    methods: {
        nameWithLang ({ startDate, endDate }) {
            return `${startDate} — ${endDate ? endDate : ''}`
        }
    },
    computed: {
        selectedVal: {
            get() {
                return this.value;
            },
            set(val) {
                this.$emit('input', val);
            }
        }
    }
});