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
            <div class="form-inline row" style="margin: 10px 0;">
                <div class="col-md-2 offset-md-4">
                    <button
                        @click.prevent="addActivity"
                        class="btn btn-outline-secondary"
                    >
                        Add
                    </button>
                </div>
                <div class="col-md-4">
                    <v-date-picker
                            mode='range'
                            v-model='range'
                            :input-props='{ readonly: true }'
                        /> 
                </div>
                <div class="col-md-2">
                <button
                    @click.prevent="deleteActivity"
                    class="btn btn-outline-secondary"
                >
                    Del
                </button>
                </div>
            </div>
    </div>
    `,
    data: function() {
        return {
            range: {
                start: null,
                end: null
            }
        }
    },
    props: {
        value: Object,
        options: {
            type: Array
        }
    },
    methods: {
        nameWithLang ({ startDate, endDate }) {
            return `${startDate} — ${endDate ? endDate : ''}`
        },
        deleteActivity() {
            this.$emit('activityDeleted', this.selectedVal);
        },
        addActivity() {
            this.$emit('activityAdded',
            {
                startDate: this.formatDate(this.range.start),
                endDate: this.formatDate(this.range.end)
            });
        },
        formatDate(date) {
            let monthNames = [
                "Jan", "Feb", "Mar",
                "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct",
                "Nov", "Dec"
            ];

            let year = date.getFullYear();
            let month = date.getMonth();
            let day = date.getDate();
            
            var hours = date.getHours();
            hours = hours > 12 ? hours - 12 : hours;
            var minutes = date.getMinutes();
            minutes = minutes < 10 ? '0' + minutes : minutes;
            var seconds = date.getSeconds();
            seconds = seconds < 10 ? '0' + seconds : seconds;

            let s = date.getHours() > 12 ? "PM" : "AM";

            return monthNames[month] + ' ' + day + ', ' + year + ' '
                    + ' ' + hours + ':' + minutes + ':' + seconds +  ' ' + s; 
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