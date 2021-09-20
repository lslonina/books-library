const path = require("path");

module.exports = function(_env, argv) {
    const isProduction = argv.mode === "production";
    const isDevelopment = !isProduction;

    return {
        devtool: isDevelopment && "cheap-module-source-map",
        entry: ['regenerator-runtime/runtime',"./src/index.js" ],
        module: {
            rules: [{
                test: /\.(js|jsx)$/,
                exclude: /node_modules/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env', '@babel/preset-react']
                    }
                }
            }, {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            }]
        },
        resolve: {
            extensions: ['.js', '.jsx']
        },
        output: {
            path: path.resolve(__dirname, "dist"),
            filename: "assets/js/[name].[contenthash:8].js",
            publicPath: "/"
        }
    };
};
