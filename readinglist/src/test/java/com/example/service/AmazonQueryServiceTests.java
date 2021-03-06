package com.example.service;

import com.example.domain.Book;
import com.example.service.exception.AmazonServiceException;
import com.example.service.exception.BookNotFoundException;
import com.example.service.exception.IsbnIllegalException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Created by Rainbow on 2017/1/16.
 */
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:resource.properties")
@ContextConfiguration(classes = AmazonQueryServiceConfiguration.class)
public class AmazonQueryServiceTests {

    @Autowired
    private AmazonQueryService amazonQueryService;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

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

    private final static String NO_RESULT_XML = "<?xml version=\"1.0\"?>\n" +
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
            "        <Item/>\n" +
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

    private final static String FORBIDDEN_RESPONSE = "<?xml version=\"1.0\"?>\n" +
            "<ItemLookupErrorResponse\n" +
            "    xmlns=\"http://ecs.amazonaws.com/doc/2005-10-05/\">\n" +
            "    <Error>\n" +
            "        <Code>InvalidClientTokenId</Code>\n" +
            "        <Message>The AWS Access Key Id you provided does not exist in our records.</Message>\n" +
            "    </Error>\n" +
            "    <RequestId>c23956a1-a55f-4b30-8acc-be803e0b5ab6</RequestId>\n" +
            "</ItemLookupErrorResponse>";

    @Before
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testQueryByISBN() throws Exception {

        mockServer.expect(queryParam("AWSAccessKeyId","AKIAJURJTJZHIXNSWDIQ"))
                .andExpect(queryParam("AssociateTag", "BookShelf"))
                .andExpect(queryParam("IdType", "ISBN"))
                .andExpect(queryParam("ItemId", "9787302423287"))
                .andExpect(queryParam("Operation", "ItemLookup"))
                .andExpect(queryParam("ResponseGroup", "Images%2CItemAttributes"))
                .andExpect(queryParam("SearchIndex", "Books"))
                .andExpect(queryParam("Service", "AWSECommerceService"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(COMPLEX_XML.replaceAll("(\\w+=\\w+)(&)", "$1&amp;"),
                        new MediaType(MediaType.APPLICATION_XML, Charset.forName("utf-8"))));

        Book expect = new Book();
        expect.setIsbn("9787302423287");
        expect.setAuthor("周志华");
        expect.setTitle("机器学习");
        expect.setPublisher("清华大学出版社");
        expect.setImageUrl("https://images-cn.ssl-images-amazon.com/images/I/410pXZz4kFL._SL75_.jpg");

        Book actual = amazonQueryService.queryByISBN("9787302423287");

        mockServer.verify();

        assertThat(actual, sameBeanAs(expect).ignoring("description"));
    }

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Test
    public void shouldThrowNotFoundBookExceptionWhenQueryFoundNothing() throws Exception {
        expected.expect(IsbnIllegalException.class);

        mockServer.expect(method(HttpMethod.GET))
                .andRespond(withSuccess(RESPONSE_WITH_ERRORS_XML.replaceAll("(\\w+=\\w+)(&)", "$1&amp;"),
                        new MediaType(MediaType.APPLICATION_XML, Charset.forName("utf-8"))));

        amazonQueryService.queryByISBN("9787302423287");

        mockServer.verify();
    }

    @Test
    public void shouldThrowAccessForbitExceptionWhenAWSReturnForbidden() throws Exception {
        String errorMsg = "Error{" +
                "code='InvalidClientTokenId'" +
                ", message='The AWS Access Key Id you provided does not exist in our records.'" +
                '}';
        expected.expect(AmazonServiceException.class);
        expected.expectMessage(errorMsg);

        mockServer.expect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.FORBIDDEN)
                        .body(FORBIDDEN_RESPONSE)
                        .contentType(new MediaType(MediaType.APPLICATION_XML, Charset.forName("utf-8"))));

        amazonQueryService.queryByISBN("9787302423287");

        mockServer.verify();

        fail("Not throw AmazonServiceException when access forbidden.");
    }

    @Test
    public void shouldThrowBookNotFoundExceptionWhenAWSNotFoundBook() throws Exception {

        expected.expect(BookNotFoundException.class);

        mockServer.expect(method(HttpMethod.GET))
                .andRespond(withSuccess(NO_RESULT_XML.replaceAll("(\\w+=\\w+)(&)", "$1&amp;"),
                        new MediaType(MediaType.APPLICATION_XML, Charset.forName("utf-8"))));

        amazonQueryService.queryByISBN("9787302423287");

        mockServer.verify();

        fail("Not throw BookNotFoundException when AWS not found any books.");

    }
}
