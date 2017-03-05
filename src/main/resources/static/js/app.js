var app = new Vue({
    el: '#app',
    data: {
        supportedCountryTaxSystems: [],
        selectedCountryIndex: 0,
        dailyNetSalary: null,
        dailyNetSalaryError: false,
        monthlyPlnNetContractSalary: {},
        errorResponse: null
    },
    mounted: function() {
        axios.get('/api/country-tax-systems', { headers: { 'Content-Type': 'application/json;charset=UTF-8' }, data: {} })
            .then(function(response) {
                app.supportedCountryTaxSystems = response.data;
            })
            .catch(function(error) {
                app.errorResponse = error.response.data;
            });
    },
    methods: {
        calculateMonthlyPlnNetContractSalary: function() {
            app.errorResponse = null;
            app.monthlyPlnNetContractSalary = {};

            if (!app.dailyNetSalary) {
                app.dailyNetSalaryError = true;
                return;
            }
            app.dailyNetSalaryError = false;
            var url = '/api/country-tax-systems/' + app.supportedCountryTaxSystems[app.selectedCountryIndex].countryCode + '/monthly-pln-net-contract-salary-calculation';
            var payload = {
                amount: app.dailyNetSalary,
                currency: app.supportedCountryTaxSystems[app.selectedCountryIndex].currencyCode
            };
            axios.post(url, payload)
                .then(function(response) {
                    app.monthlyPlnNetContractSalary = response.data;
                })
                .catch(function(error) {
                    app.errorResponse = error.response.data;
                });
        }
    }
});