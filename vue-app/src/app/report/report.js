Vue.component("report-page",{
    template: `
    <base-layout :page-title="$route.meta.title">
        <div class="row" style="margin-bottom:10px;">
            <div class="col-md-4">
                <v-date-picker
                    mode='range'
                    v-model='range'
                    :input-props='{ readonly: true }'
                />  
            </div>
            <button 
                type="button"
                class="btn btn-outline-primary"
                v-on:click="getReports"
            >
                Pick date
            </button>   
        </div>
        
        <table class="table" border=1>
            <thead class="thead-dark" v-if="reports !== null">
                <th>
                    Resource name
                </th>
                <th>
                    Price
                </th>
            </thead>
            <tr v-for="(price, resource, index) in reports">
                <td>{{resource}}</td>
                <td>{{price.toFixed(2)}}&euro;</td>
            </tr>
            <tr v-if="reports" style="border-top:3px solid black;">
                <td><strong>Total</strong></td>
                <td>{{totalPrice.toFixed(2)}}&euro;</td>
            </tr>
        </table>
    </base-layout>
    `,
    data: function(){
        return {
            range: {
                start: null,
                end : null
            },
            reports: null
        }
    },
    methods: {
        getReports: function(){
            if (this.dateSet)
                axios
                .get('/api/report?start='+ this.range.start.toLocaleDateString('en-GB') + '&end=' +  this.range.end.toLocaleDateString('en-GB'))
                .then(response => this.reports = response.data.prices)
                .catch(err => {
                    const status = err.response.status;
                    const msg = err.response.data;
                    alert('' + status + ': ' +  msg);
                })
        }
    },
    computed: {
        totalPrice() {
            var sum = 0;
            for (let [key, value] of Object.entries(this.reports)) {
                sum += value;
            }
            return sum;
        },
        dateSet() {
            return this.range.start && this.range.end;
        }
    }
})