# Introduction

### Business Context
The London Gift Shop (LGS) operates in a highly competitive online retail landscape. With a vast inventory and a global 
customer base, the sheer volume of transaction data had become overwhelming. The primary challenge was a lack of 
"customer intelligence"—the business could see total sales but couldn't distinguish between a one-time holiday shopper 
and a high-value loyalist. This project was initiated to transform raw invoice data into actionable behavioral segments 
and growth metrics.

### Business Application
LGS will utilize these analytic results to shift from "blanket marketing" (sending the same email to everyone) to 
targeted lifecycle management. By identifying "At Risk" and "Hibernating" customers through our RFM model, the marketing
team can deploy specific win-back campaigns. Simultaneously, identifying "Champions" allows LGS to create exclusive 
loyalty rewards, ensuring their most profitable customers feel valued and continue to drive revenue.

### Technologies and Methodologies
- **Jupyter Notebook** for interactive data exploration and visualization
- **Pandas, Numpy** for complex aggregations, time-series conversion, and identifying customer types
- **Matplotlib** for creating stacked bar charts and distribution plots to make data intuitive
- **RFM (Recency, Frequency, Monetary)** framework was applied in addition to monthly sales growth and active user trends

# Implementaion
## Project Architecture
The project follows a modern data pipeline flow. Data originates from the LGS Web Application, which handles customer 
orders and inventory. This data is stored in a CSV file, exported for analysis, and processed through our 
analytics engine to generate insights that are then fed back into marketing automation tools.

![Illustration of Project Architecture](/python_data_analytics/assets/archt.png)

### Flow
- **Source:** LGS Web App captures transactional events.
- **Storage:** Transactions are recorded in the LGS Database (data warehouse).
- **Analytics:** Python/Pandas extracts data to perform cleaning (handling cancellations) and RFM scoring modelling.
- **Insight:** Visualizations identify growth and retention trends.
- **Action:** Segments are exported to CRM tools for personalized marketing.


## Data Analytics and Wrangling
**View Detailed Analysis** [**Here**](./retail_data_analytics_wrangling.ipynb)
- **Strategy 1:** Analysis shows that existing users provide a more stable revenue stream. We can design a 
"Refer-a-Friend" program specifically for the "Loyal Customers" segment to lower acquisition costs.
- **Strategy 2:** After identifying "Potential Loyalists" (high recency but low frequency), we can send "Second Purchase" 
discounts to turn one-time shoppers into repeat buyers.
- **Strategy 3:** The Monthly Sales Growth chart shows seasonality, therefore LGS can now plan "Flash Sales" during 
historically slow months to even out annual cash flow.

# Potential Improvements
- Beyond "who" is buying, we would analyze "what" items are bought together. This would allow LGS to implement a 
"Frequently Bought Together" recommendation engine on their web app.
- Use the existing RFM data to train a Machine Learning model that predicts the probability of a customer leaving before
they become "Hibernating,", which allows for proactive intervention.
- Since the dataset includes a "Country" field, a major improvement would be to perform a Regional Performance Analysis, 
which allows LGS to localize marketing strategies and campaigns.  
