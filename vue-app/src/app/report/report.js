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
            class="btn btn-success"
            v-on:click="getReports"
            >
                Pick date
            </button>   
        </div>
        
        <table class="table" border=1>
            <thead class="thead-dark">
                <th>
                    Resource name
                </th>
                <th>
                    Price
                </th>
            </thead>
            <tr v-for="(price, resource) in reports">
                <td>{{resource}}</td>
                <td>{{price}}</td>
            </tr>
        </table>
    </base-layout>
    `,
    data: function(){
        return {
            range: {
                start: new Date(),
                end : new Date()
            },
            reports: {}
        }
    },
    methods: {
        getReports: function(){
            axios
            .get('/report?from='+ this.range.start.toLocaleDateString('en-GB') + '&to=' +  this.range.end.toLocaleDateString('en-GB'))
            .then(response => this.reports = response.data)
        }
    }
})