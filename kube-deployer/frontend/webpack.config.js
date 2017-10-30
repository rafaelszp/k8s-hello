const webpack = require("webpack");
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');


const ENV_CONFIG = {
    client_id: JSON.stringify('-'),
    client_secret: JSON.stringify('-'),
    development: {
        authUrl: JSON.stringify('https://localhost:8080/auth/realms/EXAMPLE/protocol/openid-connect'),
        apiUrl: JSON.stringify('http://localhost:8080/')
    },
    production: {
        authUrl: JSON.stringify('http://www.example.com/auth/realms/EXAMPLE/protocol/openid-connect'),
        apiUrl: JSON.stringify('http://www.sistemafieg.org.br/troca-senha')
    }

}

// check environment mode
const environment = process.env.NODE_ENV === 'production' ? 'production' : 'development';
console.log(`Environment: [${environment}]`);
console.log(`\tAUTH_URL: ${ENV_CONFIG[environment].authUrl},\n\tSERVER_URL: ${ENV_CONFIG[environment].serverUrl},\n\tAPI_URL: ${ENV_CONFIG[environment].apiUrl}`);

let uglify;
if(process.env.NODE_ENV === 'production'){
    uglify = new webpack.optimize.UglifyJsPlugin();
}else{
    uglify = new webpack.optimize.UglifyJsPlugin({
        test: 'no-files-to-be-uglified'
    });
}

module.exports = {
    context: __dirname + "/src",
    entry: {
        app: "./index.jsx",
    },
    output: {
        path: __dirname + "/dist",
        filename: "[name].bundle.js",
    },
    devServer: {
        port: 8080,
        host:'0.0.0.0',
        contentBase: './dist'
    },
    resolve: {
        extensions: ['.js', '.jsx'],
        alias: {
            modules: __dirname + '/node_modules',
            jquery: 'modules/admin-lte/plugins/jQuery/jquery-2.2.3.min.js',
            bootstrap: 'modules/admin-lte/bootstrap/js/bootstrap.js'
        }
    },
    plugins: [
        new webpack.ProvidePlugin({
            $: 'jquery',
            jQuery: 'jquery',
            'window.jQuery': 'jquery'
        }),
        new ExtractTextPlugin('app.css'),
        new webpack.DefinePlugin({
            'AUTH_URL': ENV_CONFIG[environment].authUrl,
            'API_URL': ENV_CONFIG[environment].apiUrl,
            'CLIENT_ID': ENV_CONFIG.client_id,
            'CLIENT_SECRET': ENV_CONFIG.client_secret,
            'ENVIRONMENT': JSON.stringify(environment),
            'process.env.NODE_ENV': JSON.stringify(environment)
        }),
        new CopyWebpackPlugin([
            {
                from: './index.html'
            }
        ],{
            debug: 'info'
        }),
        uglify
    ],
    module:{
        rules:[
            {
                test: /.js[x]?$/,
                use:[
                    {
                        loader: 'babel-loader',
                        options: {
                            presets: ['es2015', 'react'],
                            plugins: ['transform-object-rest-spread']
                        }
                    }
                    ]
            },
            {
                test: /\.css$/,
                use: ExtractTextPlugin.extract({
                    fallback: "style-loader",
                    use: "css-loader"
                })

            },
            {
                test: /\.woff|.woff2|.ttf|.eot|.svg|.png|.jpg*.*$/,
                use:[{loader: 'file-loader'}]
            },
            {
                test: /\.json$/,
                use:[{loader: 'json-loader'}]
            }
        ]
    }
};