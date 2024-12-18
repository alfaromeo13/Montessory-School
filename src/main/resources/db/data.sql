-- Insert into Parent table
INSERT INTO parent (email)
VALUES
    ('jovanvukovi09@gmail.com'),
    ('89242091@student.upr.si');

-- Insert into Admin table
INSERT INTO admin (username, password)
VALUES
    ('admin', '$2a$12$TqG7hEuu0g/ceRXZurf3qedsM25kQg273VbTB2.8xh4CnZUysUwVO'); -- admin admin

-- Insert into Child table
INSERT INTO child (parent_id, name, surname, date_of_birth, enrollment_year, grade, gender, address, postal_code, city)
VALUES
    (1, 'John', 'Doe', '2012-05-15', 2020, 'THIRD_GRADE', 'BOY', '123 Elm Street', '12345', 'Springfield'),
    (1, 'Jane', 'Doe', '2014-08-22', 2022, 'FIRST_GRADE', 'GIRL', '123 Elm Street', '12345', 'Springfield'),
    (2, 'Alice', 'Smith', '2013-07-10', 2021, 'SECOND_GRADE', 'GIRL', '456 Maple Avenue', '67890', 'Greenville'),
    (2, 'Bob', 'Brown', '2011-11-03', 2019, 'FOURTH_GRADE', 'PREFER_NOT_TO_SAY', '789 Oak Lane', '54321', 'Hilltown');


-- Inserting dummy data into the event table

INSERT INTO event (content_blocks, event_date)
VALUES
    (
        '
        {
            "title": "School celebration!",
            "contentBlocks": [
                { "type": "text", "value": "Join us for a celebration of spring with live music, food stalls, and games for all ages." },
                {
                    "type": "image",
                    "values": [
                        "https://dedis.s3.eu-north-1.amazonaws.com/field_trip.jpg"
                    ],
                    "imageCount" : 1
                },
                { "type": "text", "value": "The event will take place at Central Park starting at 10 AM." },
                { "type": "image",
                  "values": [
                        "https://dedis.s3.eu-north-1.amazonaws.com/central_park.jpg"
                  ],
                    "imageCount" : 1
                }
            ]
        }',
        '2024-04-15 10:00:00'
    ),
    (
    '
    {
    "title": "School anniversary",
    "contentBlocks": [
        {"type": "text", "value": "Join us for our school''s annual anniversary event celebrating achievements and milestones."},
        {
              "type": "image",
              "values": [
              "https://dedis.s3.eu-north-1.amazonaws.com/570-school-anniversary-46189998.jpg"
              ],
              "imageCount" : 1
        },
        {"type": "text", "value": "There will be speeches, performances, and a gala dinner to close the evening."},
        {
              "type": "image",
              "value": [
              "https://dedis.s3.eu-north-1.amazonaws.com/charisma-624032-4268904918.jpg"
              ],
              "imageCount" : 1
        }
    ]
}',
        '2024-05-20 09:00:00'
);
