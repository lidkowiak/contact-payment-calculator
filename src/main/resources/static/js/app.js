var app = new Vue({
    el: '#app',
    data: {
        supportedCountryTaxSystems: [],
        selectedCountryIndex: 0,
        dailyNetSalary: null,
        monthlyPlnNetContractSalary: 0
    },
    mounted : function() {
        axios.get('/api/country-tax-systems', { headers : {'Content-Type' : 'application/json;charset=UTF-8'}, data : {}})
                  .then(function (response) {
                   app.supportedCountryTaxSystems = response.data;
                  })
                  .catch(function (error) {
                    console.log(error);
                  });
    },
    methods : {
        calculateMonthlyPlnNetContractSalary : function() {
            var url = '/api/country-tax-systems/' + app.supportedCountryTaxSystems[app.selectedCountryIndex].countryCode + '/monthly-pln-net-contract-salary-calculation';
            axios.post(url, {
                    amount : app.dailyNetSalary,
                    currency : app.supportedCountryTaxSystems[app.selectedCountryIndex].currencyCode
            })
            .then(function (response) {
                app.monthlyPlnNetContractSalary = response.data;
            })
            .catch(function (error) {
                console.log(error);
            });
        }
    }
});