package com.example.service;

import com.example.domain.amazon.AmazonErrorResponse;
import com.example.domain.amazon.AmazonResponse;
import org.junit.Before;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by rainbow on 2017/1/29.
 */
public class DeserialXml2ObjectTests {

    private final static String COMPLEX_XML = "<?xml version=\"1.0\"?>\n" +
            "<ItemLookupResponse\n" +
            "    xmlns=\"http://webservices.amazon.com/AWSECommerceService/2011-08-01\">\n" +
            "    <OperationRequest>\n" +
            "        <HTTPHeaders>\n" +
            "            <Header Name=\"UserAgent\" Value=\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:50.0) Gecko/20100101 Firefox/50.0\"></Header>\n" +
            "        </HTTPHeaders>\n" +
            "        <RequestId>8cc398a4-611d-411a-9931-19fb621d34c7</RequestId>\n" +
            "        <Arguments>\n" +
            "            <Argument Name=\"AWSAccessKeyId\" Value=\"AKIAIOXSEN3ZLCWQABGA\"></Argument>\n" +
            "            <Argument Name=\"AssociateTag\" Value=\"BookShelf\"></Argument>\n" +
            "            <Argument Name=\"IdType\" Value=\"ISBN\"></Argument>\n" +
            "            <Argument Name=\"ItemId\" Value=\"9787302423287\"></Argument>\n" +
            "            <Argument Name=\"Operation\" Value=\"ItemLookup\"></Argument>\n" +
            "            <Argument Name=\"ResponseGroup\" Value=\"Images,ItemAttributes\"></Argument>\n" +
            "            <Argument Name=\"SearchIndex\" Value=\"Books\"></Argument>\n" +
            "            <Argument Name=\"Service\" Value=\"AWSECommerceService\"></Argument>\n" +
            "            <Argument Name=\"Timestamp\" Value=\"2017-01-28T16:09:03.000Z\"></Argument>\n" +
            "            <Argument Name=\"Signature\" Value=\"/T4LeC7WiFWpDp2M8S1WmWNfugRuYuyzgNpaIXCPW0w=\"></Argument>\n" +
            "        </Arguments>\n" +
            "        <RequestProcessingTime>0.0654821280000000</RequestProcessingTime>\n" +
            "    </OperationRequest>\n" +
            "    <Items>\n" +
            "        <Request>\n" +
            "            <IsValid>True</IsValid>\n" +
            "            <ItemLookupRequest>\n" +
            "                <ReviewPage>1</ReviewPage>\n" +
            "                <DeliveryMethod>Ship</DeliveryMethod>\n" +
            "                <ReviewSort>-SubmissionDate</ReviewSort>\n" +
            "                <IdType>ISBN</IdType>\n" +
            "                <ItemId>9787302423287</ItemId>\n" +
            "                <ResponseGroup>Images</ResponseGroup>\n" +
            "                <ResponseGroup>ItemAttributes</ResponseGroup>\n" +
            "                <SearchIndex>Books</SearchIndex>\n" +
            "                <VariationPage>All</VariationPage>\n" +
            "            </ItemLookupRequest>\n" +
            "        </Request>\n" +
            "        <Item>\n" +
            "            <ASIN>B01ARKEV1G</ASIN>\n" +
            "            <DetailPageURL><![CDATA[https://www.amazon.cn/%E6%9C%BA%E5%99%A8%E5%AD%A6%E4%B9%A0-%E5%91%A8%E5%BF%97%E5%8D%8E/dp/B01ARKEV1G?SubscriptionId=AKIAIOXSEN3ZLCWQABGA&tag=BookShelf&linkCode=xm2&camp=2025&creative=165953&creativeASIN=B01ARKEV1G]]></DetailPageURL>\n" +
            "            <ItemLinks>\n" +
            "                <ItemLink>\n" +
            "                    <Description>Add To Wishlist</Description>\n" +
            "                    <URL>https://www.amazon.cn/gp/registry/wishlist/add-item.html?asin.0=B01ARKEV1G&SubscriptionId=AKIAIOXSEN3ZLCWQABGA&tag=BookShelf&linkCode=xm2&camp=2025&creative=165953&creativeASIN=B01ARKEV1G</URL>\n" +
            "                </ItemLink>\n" +
            "                <ItemLink>\n" +
            "                    <Description>Tell A Friend</Description>\n" +
            "                    <URL>https://www.amazon.cn/gp/pdp/taf/B01ARKEV1G?SubscriptionId=AKIAIOXSEN3ZLCWQABGA&tag=BookShelf&linkCode=xm2&camp=2025&creative=165953&creativeASIN=B01ARKEV1G</URL>\n" +
            "                </ItemLink>\n" +
            "                <ItemLink>\n" +
            "                    <Description>All Customer Reviews</Description>\n" +
            "                    <URL>https://www.amazon.cn/review/product/B01ARKEV1G?SubscriptionId=AKIAIOXSEN3ZLCWQABGA&tag=BookShelf&linkCode=xm2&camp=2025&creative=165953&creativeASIN=B01ARKEV1G</URL>\n" +
            "                </ItemLink>\n" +
            "                <ItemLink>\n" +
            "                    <Description>All Offers</Description>\n" +
            "                    <URL>https://www.amazon.cn/gp/offer-listing/B01ARKEV1G?SubscriptionId=AKIAIOXSEN3ZLCWQABGA&tag=BookShelf&linkCode=xm2&camp=2025&creative=165953&creativeASIN=B01ARKEV1G</URL>\n" +
            "                </ItemLink>\n" +
            "            </ItemLinks>\n" +
            "            <SmallImage>\n" +
            "                <URL>https://images-cn.ssl-images-amazon.com/images/I/410pXZz4kFL._SL75_.jpg</URL>\n" +
            "                <Height Units=\"pixels\">75</Height>\n" +
            "                <Width Units=\"pixels\">67</Width>\n" +
            "            </SmallImage>\n" +
            "            <MediumImage>\n" +
            "                <URL>https://images-cn.ssl-images-amazon.com/images/I/410pXZz4kFL._SL160_.jpg</URL>\n" +
            "                <Height Units=\"pixels\">160</Height>\n" +
            "                <Width Units=\"pixels\">144</Width>\n" +
            "            </MediumImage>\n" +
            "            <LargeImage>\n" +
            "                <URL>https://images-cn.ssl-images-amazon.com/images/I/410pXZz4kFL.jpg</URL>\n" +
            "                <Height Units=\"pixels\">500</Height>\n" +
            "                <Width Units=\"pixels\">449</Width>\n" +
            "            </LargeImage>\n" +
            "            <ImageSets>\n" +
            "                <ImageSet Category=\"primary\">\n" +
            "                    <SwatchImage>\n" +
            "                        <URL>https://images-cn.ssl-images-amazon.com/images/I/410pXZz4kFL._SL30_.jpg</URL>\n" +
            "                        <Height Units=\"pixels\">30</Height>\n" +
            "                        <Width Units=\"pixels\">27</Width>\n" +
            "                    </SwatchImage>\n" +
            "                    <SmallImage>\n" +
            "                        <URL>https://images-cn.ssl-images-amazon.com/images/I/410pXZz4kFL._SL75_.jpg</URL>\n" +
            "                        <Height Units=\"pixels\">75</Height>\n" +
            "                        <Width Units=\"pixels\">67</Width>\n" +
            "                    </SmallImage>\n" +
            "                    <ThumbnailImage>\n" +
            "                        <URL>https://images-cn.ssl-images-amazon.com/images/I/410pXZz4kFL._SL75_.jpg</URL>\n" +
            "                        <Height Units=\"pixels\">75</Height>\n" +
            "                        <Width Units=\"pixels\">67</Width>\n" +
            "                    </ThumbnailImage>\n" +
            "                    <TinyImage>\n" +
            "                        <URL>https://images-cn.ssl-images-amazon.com/images/I/410pXZz4kFL._SL110_.jpg</URL>\n" +
            "                        <Height Units=\"pixels\">110</Height>\n" +
            "                        <Width Units=\"pixels\">99</Width>\n" +
            "                    </TinyImage>\n" +
            "                    <MediumImage>\n" +
            "                        <URL>https://images-cn.ssl-images-amazon.com/images/I/410pXZz4kFL._SL160_.jpg</URL>\n" +
            "                        <Height Units=\"pixels\">160</Height>\n" +
            "                        <Width Units=\"pixels\">144</Width>\n" +
            "                    </MediumImage>\n" +
            "                    <LargeImage>\n" +
            "                        <URL>https://images-cn.ssl-images-amazon.com/images/I/410pXZz4kFL.jpg</URL>\n" +
            "                        <Height Units=\"pixels\">500</Height>\n" +
            "                        <Width Units=\"pixels\">449</Width>\n" +
            "                    </LargeImage>\n" +
            "                </ImageSet>\n" +
            "            </ImageSets>\n" +
            "            <ItemAttributes>\n" +
            "                <Author>周志华</Author>\n" +
            "                <Binding>平装</Binding>\n" +
            "                <Brand>清华大学出版社</Brand>\n" +
            "                <Creator Role=\"作者\">周志华</Creator>\n" +
            "                <EAN>9787302423287</EAN>\n" +
            "                <Edition>第1版</Edition>\n" +
            "                <Feature>机器学习 - 周志华</Feature>\n" +
            "                <IsAdultProduct>0</IsAdultProduct>\n" +
            "                <ISBN>7302423288</ISBN>\n" +
            "                <Label>清华大学出版社</Label>\n" +
            "                <Languages>\n" +
            "                    <Language>\n" +
            "                        <Name>chinese</Name>\n" +
            "                        <Type>published</Type>\n" +
            "                    </Language>\n" +
            "                </Languages>\n" +
            "                <ListPrice>\n" +
            "                    <Amount>8800</Amount>\n" +
            "                    <CurrencyCode>CNY</CurrencyCode>\n" +
            "                    <FormattedPrice>￥ 88.00</FormattedPrice>\n" +
            "                </ListPrice>\n" +
            "                <Manufacturer>清华大学出版社</Manufacturer>\n" +
            "                <NumberOfPages>425</NumberOfPages>\n" +
            "                <PackageDimensions>\n" +
            "                    <Height Units=\"inches\">0.94</Height>\n" +
            "                    <Length Units=\"inches\">9.21</Length>\n" +
            "                    <Weight Units=\"pounds\">2.07</Weight>\n" +
            "                    <Width Units=\"inches\">8.27</Width>\n" +
            "                </PackageDimensions>\n" +
            "                <ProductGroup>Book</ProductGroup>\n" +
            "                <ProductTypeName>ABIS_BOOK</ProductTypeName>\n" +
            "                <PublicationDate>2016-01-01</PublicationDate>\n" +
            "                <Publisher>清华大学出版社</Publisher>\n" +
            "                <ReleaseDate>2016-02-01</ReleaseDate>\n" +
            "                <Studio>清华大学出版社</Studio>\n" +
            "                <Title>机器学习</Title>\n" +
            "            </ItemAttributes>\n" +
            "        </Item>\n" +
            "    </Items>\n" +
            "</ItemLookupResponse>";

    private final static String SIMPLE_XML = "<ItemLookupResponse>\n" +
            "    <Items>\n" +
            "        <Request>\n" +
            "            <IsValid>True</IsValid>\n" +
            "        </Request>\n" +
            "        <Item>\n" +
            "            <SmallImage>\n" +
            "                <URL>https://images-cn.ssl-images-amazon.com/images/I/410pXZz4kFL._SL75_.jpg</URL>\n" +
            "                <Height Units=\"pixels\">75</Height>\n" +
            "                <Width Units=\"pixels\">67</Width>\n" +
            "            </SmallImage>\n" +
            "            <MediumImage>\n" +
            "                <URL>https://images-cn.ssl-images-amazon.com/images/I/410pXZz4kFL._SL160_.jpg</URL>\n" +
            "                <Height Units=\"pixels\">160</Height>\n" +
            "                <Width Units=\"pixels\">144</Width>\n" +
            "            </MediumImage>\n" +
            "            <LargeImage>\n" +
            "                <URL>https://images-cn.ssl-images-amazon.com/images/I/410pXZz4kFL.jpg</URL>\n" +
            "                <Height Units=\"pixels\">500</Height>\n" +
            "                <Width Units=\"pixels\">449</Width>\n" +
            "            </LargeImage>\n" +
            "            <ItemAttributes>\n" +
            "                <Author>周志华</Author>\n" +
            "                <Binding>平装</Binding>\n" +
            "                <EAN>9787302423287</EAN>\n" +
            "                <Creator Role=\"作者\">周志华</Creator>\n" +
            "                <Edition>第1版</Edition>\n" +
            "                <ISBN>7302423288</ISBN>\n" +
            "                <NumberOfPages>425</NumberOfPages>\n" +
            "                <PublicationDate>2016-01-01</PublicationDate>\n" +
            "                <Publisher>清华大学出版社</Publisher>\n" +
            "                <Title>机器学习</Title>\n" +
            "            </ItemAttributes>\n" +
            "        </Item>\n" +
            "    </Items>\n" +
            "</ItemLookupResponse>";

    private final static String RESPONSE_WITH_ERRORS_XML = "<ItemLookupResponse>\n" +
            "    <Items>\n" +
            "        <Request>\n" +
            "            <IsValid>True</IsValid>\n\n" +
            "            <ItemLookupRequest>\n" +
            "                <ItemId>9787513537100</ItemId>\n" +
            "            </ItemLookupRequest>" +
            "            <Errors>\n" +
            "                <Error>\n" +
            "                    <Code>AWS.InvalidParameterValue</Code>\n" +
            "                    <Message>9787302423286 is not a valid value for ItemId. Please change this value and retry your request.</Message>\n" +
            "                </Error>\n" +
            "            </Errors>" +
            "        </Request>\n" +
            "    </Items>\n" +
            "</ItemLookupResponse>";

    private final static String ERROR_RESPONSE_XML = "<?xml version=\"1.0\"?>\n" +
            "<ItemLookupErrorResponse\n" +
            "    xmlns=\"http://ecs.amazonaws.com/doc/2005-10-05/\">\n" +
            "    <Error>\n" +
            "        <Code>InvalidClientTokenId</Code>\n" +
            "        <Message>The AWS Access Key Id you provided does not exist in our records.</Message>\n" +
            "    </Error>\n" +
            "    <RequestId>c23956a1-a55f-4b30-8acc-be803e0b5ab6</RequestId>\n" +
            "</ItemLookupErrorResponse>";

    private Serializer serializer;

    @Before
    public void setUp() {
        serializer = new Persister();
    }

    @Test
    public void testDeserializeAmazonBooksFromSimpleXml() throws Exception {

        AmazonResponse bookList = serializer.read(AmazonResponse.class, SIMPLE_XML, false);

        assertThat(bookList.getData().getRequest().isValid(), is(true));

        assertThat(bookList.getData().getRequest().hasErrors(), is(false));

        assertThat(bookList.getData().getBooks().size(), equalTo(1));

        assertThat(bookList.getData().getBooks().get(0).getSmallImage(), notNullValue());
        assertThat(bookList.getData().getBooks().get(0).getSmallImage().getHeight().getValue(), equalTo(75));
        assertThat(bookList.getData().getBooks().get(0).getSmallImage().getWidth().getUnits(), equalTo("pixels"));

        assertThat(bookList.getData().getBooks().get(0).getMediumImage(), notNullValue());
        assertThat(bookList.getData().getBooks().get(0).getMediumImage().getHeight().getValue(), equalTo(160));
        assertThat(bookList.getData().getBooks().get(0).getMediumImage().getWidth().getValue(), equalTo(144));

        assertThat(bookList.getData().getBooks().get(0).getLargeImage(), notNullValue());
        assertThat(bookList.getData().getBooks().get(0).getLargeImage().getHeight().getValue(), equalTo(500));
        assertThat(bookList.getData().getBooks().get(0).getLargeImage().getWidth().getValue(), equalTo(449));

        assertThat(bookList.getData().getBooks().get(0).getAttributes().getTitle(), equalTo("机器学习"));
        assertThat(bookList.getData().getBooks().get(0).getAttributes().getAuthor(), equalTo("周志华"));
        assertThat(bookList.getData().getBooks().get(0).getAttributes().getPublicationDate(), equalTo("2016-01-01"));
        assertThat(bookList.getData().getBooks().get(0).getAttributes().getEan(), equalTo("9787302423287"));
    }

    @Test
    public void testDeserializeErrorsFromXml() throws Exception {

        AmazonResponse bookList = serializer.read(AmazonResponse.class, RESPONSE_WITH_ERRORS_XML, false);

        assertThat(bookList.getData().getRequest().isValid(), is(true));
        assertThat(bookList.getData().getRequest().hasErrors(), is(true));
        assertThat(bookList.getData().getRequest().getErrors(), notNullValue());
        assertThat(bookList.getData().getRequest().getErrors().size(), equalTo(1));
        assertThat(bookList.getData().getRequest().getErrors().get(0).getCode(), equalTo("AWS.InvalidParameterValue"));
        assertThat(bookList.getData().getRequest().getItemId(), equalTo("9787513537100"));

    }


    @Test
    public void testDeserializeAmazonBooksFromComplexXml() throws Exception {

        // Using regex replace & in url with &amp;. Because & in xml is stop word.
        AmazonResponse bookList = serializer.read(AmazonResponse.class, COMPLEX_XML.replaceAll("(\\w+=\\w+)(&)", "$1&amp;"), false);

        assertThat(bookList.getData().getRequest().isValid(), is(true));

        assertThat(bookList.getData().getRequest().hasErrors(), is(false));

        assertThat(bookList.getData().getBooks().size(), equalTo(1));

        assertThat(bookList.getData().getBooks().get(0).getSmallImage(), notNullValue());
        assertThat(bookList.getData().getBooks().get(0).getSmallImage().getHeight().getValue(), equalTo(75));
        assertThat(bookList.getData().getBooks().get(0).getSmallImage().getWidth().getUnits(), equalTo("pixels"));

        assertThat(bookList.getData().getBooks().get(0).getMediumImage(), notNullValue());
        assertThat(bookList.getData().getBooks().get(0).getMediumImage().getHeight().getValue(), equalTo(160));
        assertThat(bookList.getData().getBooks().get(0).getMediumImage().getWidth().getValue(), equalTo(144));

        assertThat(bookList.getData().getBooks().get(0).getLargeImage(), notNullValue());
        assertThat(bookList.getData().getBooks().get(0).getLargeImage().getHeight().getValue(), equalTo(500));
        assertThat(bookList.getData().getBooks().get(0).getLargeImage().getWidth().getValue(), equalTo(449));

        assertThat(bookList.getData().getBooks().get(0).getAttributes().getTitle(), equalTo("机器学习"));
        assertThat(bookList.getData().getBooks().get(0).getAttributes().getAuthor(), equalTo("周志华"));
        assertThat(bookList.getData().getBooks().get(0).getAttributes().getPublicationDate(), equalTo("2016-01-01"));
    }

    @Test
    public void testUseRegexReplaceAndInUrl() throws Exception {

        String expect = "<URL>https://www.amazon.cn/gp/pdp/taf/B01ARKEV1G?SubscriptionId=AKIAIOXSEN3ZLCWQABGA&amp;tag=BookShelf&amp;linkCode=xm2&amp;camp=2025&amp;creative=165953&amp;creativeASIN=B01ARKEV1G</URL>";

        String actual = "<URL>https://www.amazon.cn/gp/pdp/taf/B01ARKEV1G?SubscriptionId=AKIAIOXSEN3ZLCWQABGA&tag=BookShelf&linkCode=xm2&camp=2025&creative=165953&creativeASIN=B01ARKEV1G</URL>".replaceAll("(\\w+=\\w+)(&)", "$1&amp;");

        assertThat(actual, equalTo(expect));
    }

    @Test
    public void testDeserializeFromXmlString() throws Exception {

        String xml = "<?xml version=\"1.0\" ?><ItemLookupResponse xmlns=\"http://webservices.amazon.com/AWSECommerceService/2011-08-01\"><OperationRequest><HTTPHeaders><Header Name=\"UserAgent\" Value=\"Java/1.8.0_112\"></Header></HTTPHeaders><RequestId>a14dc09c-f53e-4542-837f-1ff69eb10cd6</RequestId><Arguments><Argument Name=\"AWSAccessKeyId\" Value=\"AKIAIOXSEN3ZLCWQABGA\"></Argument><Argument Name=\"AssociateTag\" Value=\"BookShelf\"></Argument><Argument Name=\"IdType\" Value=\"ISBN\"></Argument><Argument Name=\"ItemId\" Value=\"9787302423287\"></Argument><Argument Name=\"Operation\" Value=\"ItemLookup\"></Argument><Argument Name=\"ResponseGroup\" Value=\"Images,ItemAttributes\"></Argument><Argument Name=\"SearchIndex\" Value=\"Books\"></Argument><Argument Name=\"Service\" Value=\"AWSECommerceService\"></Argument><Argument Name=\"Timestamp\" Value=\"2017-02-06T13:01:22Z\"></Argument><Argument Name=\"Signature\" Value=\"9uhDZyavTbJQe3msh6YyyRUkaHr8kVo+hy9wulfiySU=\"></Argument></Arguments><RequestProcessingTime>0.0751446510000000</RequestProcessingTime></OperationRequest><Items><Request><IsValid>True</IsValid><ItemLookupRequest><ReviewPage>1</ReviewPage><DeliveryMethod>Ship</DeliveryMethod><ReviewSort>-SubmissionDate</ReviewSort><IdType>ISBN</IdType><ItemId>9787302423287</ItemId><ResponseGroup>Images</ResponseGroup><ResponseGroup>ItemAttributes</ResponseGroup><SearchIndex>Books</SearchIndex><VariationPage>All</VariationPage></ItemLookupRequest></Request><Item><ASIN>B01ARKEV1G</ASIN><DetailPageURL>https://www.amazon.cn/%E6%9C%BA%E5%99%A8%E5%AD%A6%E4%B9%A0-%E5%91%A8%E5%BF%97%E5%8D%8E/dp/B01ARKEV1G?SubscriptionId=AKIAIOXSEN3ZLCWQABGA&amp;tag=BookShelf&amp;linkCode=xm2&amp;camp=2025&amp;creative=165953&amp;creativeASIN=B01ARKEV1G</DetailPageURL><ItemLinks><ItemLink><Description>Add To Wishlist</Description><URL>https://www.amazon.cn/gp/registry/wishlist/add-item.html?asin.0=B01ARKEV1G&amp;SubscriptionId=AKIAIOXSEN3ZLCWQABGA&amp;tag=BookShelf&amp;linkCode=xm2&amp;camp=2025&amp;creative=165953&amp;creativeASIN=B01ARKEV1G</URL></ItemLink><ItemLink><Description>Tell A Friend</Description><URL>https://www.amazon.cn/gp/pdp/taf/B01ARKEV1G?SubscriptionId=AKIAIOXSEN3ZLCWQABGA&amp;tag=BookShelf&amp;linkCode=xm2&amp;camp=2025&amp;creative=165953&amp;creativeASIN=B01ARKEV1G</URL></ItemLink><ItemLink><Description>All Customer Reviews</Description><URL>https://www.amazon.cn/review/product/B01ARKEV1G?SubscriptionId=AKIAIOXSEN3ZLCWQABGA&amp;tag=BookShelf&amp;linkCode=xm2&amp;camp=2025&amp;creative=165953&amp;creativeASIN=B01ARKEV1G</URL></ItemLink><ItemLink><Description>All Offers</Description><URL>https://www.amazon.cn/gp/offer-listing/B01ARKEV1G?SubscriptionId=AKIAIOXSEN3ZLCWQABGA&amp;tag=BookShelf&amp;linkCode=xm2&amp;camp=2025&amp;creative=165953&amp;creativeASIN=B01ARKEV1G</URL></ItemLink></ItemLinks><SmallImage><URL>https://images-cn.ssl-images-amazon.com/images/I/410pXZz4kFL._SL75_.jpg</URL><Height Units=\"pixels\">75</Height><Width Units=\"pixels\">67</Width></SmallImage><MediumImage><URL>https://images-cn.ssl-images-amazon.com/images/I/410pXZz4kFL._SL160_.jpg</URL><Height Units=\"pixels\">160</Height><Width Units=\"pixels\">144</Width></MediumImage><LargeImage><URL>https://images-cn.ssl-images-amazon.com/images/I/410pXZz4kFL.jpg</URL><Height Units=\"pixels\">500</Height><Width Units=\"pixels\">449</Width></LargeImage><ImageSets><ImageSet Category=\"primary\"><SwatchImage><URL>https://images-cn.ssl-images-amazon.com/images/I/410pXZz4kFL._SL30_.jpg</URL><Height Units=\"pixels\">30</Height><Width Units=\"pixels\">27</Width></SwatchImage><SmallImage><URL>https://images-cn.ssl-images-amazon.com/images/I/410pXZz4kFL._SL75_.jpg</URL><Height Units=\"pixels\">75</Height><Width Units=\"pixels\">67</Width></SmallImage><ThumbnailImage><URL>https://images-cn.ssl-images-amazon.com/images/I/410pXZz4kFL._SL75_.jpg</URL><Height Units=\"pixels\">75</Height><Width Units=\"pixels\">67</Width></ThumbnailImage><TinyImage><URL>https://images-cn.ssl-images-amazon.com/images/I/410pXZz4kFL._SL110_.jpg</URL><Height Units=\"pixels\">110</Height><Width Units=\"pixels\">99</Width></TinyImage><MediumImage><URL>https://images-cn.ssl-images-amazon.com/images/I/410pXZz4kFL._SL160_.jpg</URL><Height Units=\"pixels\">160</Height><Width Units=\"pixels\">144</Width></MediumImage><LargeImage><URL>https://images-cn.ssl-images-amazon.com/images/I/410pXZz4kFL.jpg</URL><Height Units=\"pixels\">500</Height><Width Units=\"pixels\">449</Width></LargeImage></ImageSet></ImageSets><ItemAttributes><Author>周志华</Author><Binding>平装</Binding><Brand>清华大学出版社</Brand><Creator Role=\"作者\">周志华</Creator><EAN>9787302423287</EAN><Edition>第1版</Edition><Feature>机器学习 - 周志华</Feature><IsAdultProduct>0</IsAdultProduct><ISBN>7302423288</ISBN><Label>清华大学出版社</Label><Languages><Language><Name>chinese</Name><Type>published</Type></Language></Languages><ListPrice><Amount>8800</Amount><CurrencyCode>CNY</CurrencyCode><FormattedPrice>￥ 88.00</FormattedPrice></ListPrice><Manufacturer>清华大学出版社</Manufacturer><NumberOfPages>425</NumberOfPages><PackageDimensions><Height Units=\"inches\">0.94</Height><Length Units=\"inches\">9.21</Length><Weight Units=\"pounds\">2.07</Weight><Width Units=\"inches\">8.27</Width></PackageDimensions><ProductGroup>Book</ProductGroup><ProductTypeName>ABIS_BOOK</ProductTypeName><PublicationDate>2016-01-01</PublicationDate><Publisher>清华大学出版社</Publisher><ReleaseDate>2016-02-01</ReleaseDate><Studio>清华大学出版社</Studio><Title>机器学习</Title></ItemAttributes></Item></Items></ItemLookupResponse>";

        AmazonResponse bookList = serializer.read(AmazonResponse.class, xml, false);

        assertThat(bookList.getData().getRequest().isValid(), is(true));

        assertThat(bookList.getData().getRequest().hasErrors(), is(false));

        assertThat(bookList.getData().getBooks().size(), equalTo(1));

    }

    @Test
    public void testDeserializeFromErrorResponse() throws Exception {

        AmazonErrorResponse error = serializer.read(AmazonErrorResponse.class, ERROR_RESPONSE_XML, false);

        assertThat(error, notNullValue());
        assertThat(error.getError(), notNullValue());
        assertThat(error.getError().getCode(), equalTo("InvalidClientTokenId"));
        assertThat(error.getError().getMessage(), equalTo("The AWS Access Key Id you provided does not exist in our records."));
    }
}